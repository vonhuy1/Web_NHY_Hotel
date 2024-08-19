package hotel.Controller;

import freemarker.template.Configuration;
import hotel.Dto.BookingDTO;
import hotel.Event.OnBookingSuccessEvent;
import hotel.Model.Checkin;
import hotel.Model.CheckinCalendar;
import hotel.Model.Checkout;
import hotel.Model.Room;
import hotel.Paypal.PayPalResult;
import hotel.Paypal.PayPalSucess;
import hotel.Services.ITraPhong;
import hotel.Services.IttkhService;
import hotel.Services.LichDatPhongService;
import hotel.Services.PaypalServices;
import hotel.Services.QuanLyPhongService;
import hotel.hepler.ZXingHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
@Controller
public class HomeController {


    @Autowired
    private QuanLyPhongService quanLyPhongService;

    @Autowired
    private LichDatPhongService lichDatPhongService;

    @Autowired
    private ITraPhong iTraPhong;

    @Autowired
    private IttkhService ittkhService;

	@Autowired
	private PaypalServices paypalServices;
	
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @RequestMapping({"/", "/home"})
    public String index(ModelMap model) {
        return "home";
    }


    @ModelAttribute(name = "changeURL")
    public String changeURL() {
        return "dptp";
    }

    
    //Search Room
    @GetMapping("/search-available")
    public String searchAvailable(@RequestParam("checkInDate") String checkInDate,
                                  @RequestParam("checkOutDate") String checkOutDate,
                                  @RequestParam("typeRoom") String typeRoom,
                                  @RequestParam("maxPrice") double maxPrice,
                                  Model model) {

        List<Room> phongs = quanLyPhongService.findAllByGiaPhongLessThanAndLoaiPhongTenLoaiPhong(maxPrice, typeRoom);
        List<Room> availbleRooms = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (Room phong : phongs) {
            if (ittkhService.findAllByRoomMaPhongOrderByNgayDatDesc(phong.getMaPhong()).isEmpty()) {

                availbleRooms.add(phong);
            } else {
                boolean checkDate = true;

                for (Checkin datPhong : ittkhService.findAllByRoomMaPhongOrderByNgayDatDesc(phong.getMaPhong())) {

                    Checkout traPhong = iTraPhong.getByDatPhongMaDatPhong(datPhong.getMaDatPhong());
                    if (traPhong == null) {

                        continue;
                    } else if ((simpleDateFormat.format(datPhong.getNgayDat()).compareTo(checkInDate) < 1 && simpleDateFormat.format(traPhong.getNgayTra()).compareTo(checkOutDate) > -1)) {

                        checkDate = false;
                        break;
                    }
                }
                if (checkDate)
                    availbleRooms.add(phong);
            }
        }

        model.addAttribute("listRoom", availbleRooms);
        return "room";
    }

    
    //Booking
    @RequestMapping(value = "/booking", method = RequestMethod.POST)
    public String bookingRoom(@Valid @ModelAttribute("bookingDTO") BookingDTO bookingDTO, Model model, WebRequest request) throws ParseException {
        Room phong = quanLyPhongService.getByMaPhong(bookingDTO.getRoomCode());
        if (phong == null) {
            if (!quanLyPhongService.findAllByLoaiPhongTenLoaiPhong(bookingDTO.getRoomType()).isEmpty()) {
                List<Room> phongs = quanLyPhongService.findAllByLoaiPhongTenLoaiPhong(bookingDTO.getRoomType());
                for (Room p : phongs) {
                    if (lichDatPhongService.findAllByRoomMaPhongOrderByNgayDatDesc(p.getMaPhong()).isEmpty()) {
                        phong = p;
                        break;
                    } else {

                        if (checkDate(p, bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate())) {
                            phong = p;
                            break;
                        }
                    }
                }
            } else {
                model.addAttribute("error", "Out of room");
                return "booking";
            }
        } else {
            if (!lichDatPhongService.findAllByRoomMaPhongOrderByNgayDatDesc(phong.getMaPhong()).isEmpty()) {

                if (!checkDate(phong, bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate())) {
                    model.addAttribute("error", "Room has been reserved");
                    return "booking";
                }

            }
        }

        if (phong == null) {
            model.addAttribute("error", "Out of room");
            return "booking";
        }

        if (bookingDTO.getCheckInDate().compareToIgnoreCase(new SimpleDateFormat("yyyy-MM-dd").format(new Date())) < 0) {
            System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            System.out.println(bookingDTO.getCheckInDate());
            model.addAttribute("error", "Check in date can greater or equal today");
            return "booking";
        }


        if (bookingDTO.getCheckInDate().compareTo(bookingDTO.getCheckOutDate()) > -1) {
            model.addAttribute("error", "Check in date can't greater check out date");
            return "booking";
        }

        java.sql.Date sqlDate = java.sql.Date.valueOf(String.valueOf(bookingDTO.getCheckInDate()));
        java.sql.Date sqlDate1 = java.sql.Date.valueOf(String.valueOf(bookingDTO.getCheckOutDate()));
        CheckinCalendar checkinCalendar = new CheckinCalendar((int) (lichDatPhongService.countfindAll() + 1),

                bookingDTO.getName(),
                bookingDTO.getPhoneNumber(),
                bookingDTO.getEmail(),

                phong,

                sqlDate,
                sqlDate1
        );

        lichDatPhongService.save(checkinCalendar);

        try {
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnBookingSuccessEvent(bookingDTO, appUrl, phong));
        } catch (Exception re) {
            re.printStackTrace();
        }
        model.addAttribute("errors", "Successful reservation");
        return "booking";
    }

    private boolean checkDate(Room phong, String checkInDate, String checkOutDate) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        boolean checkDate = true;

        for (Checkin datPhong : ittkhService.findAllByRoomMaPhongOrderByNgayDatDesc(phong.getMaPhong())) {

            Checkout traPhong = iTraPhong.getByDatPhongMaDatPhong(datPhong.getMaDatPhong());
            if (traPhong != null)
                if ((simpleDateFormat.format(datPhong.getNgayDat()).compareTo(checkInDate) < 1 && simpleDateFormat.format(traPhong.getNgayTra()).compareTo(checkInDate) > -1) ||
                        (simpleDateFormat.format(datPhong.getNgayDat()).compareTo(checkOutDate) < 1 && simpleDateFormat.format(traPhong.getNgayTra()).compareTo(checkOutDate) > -1) ||
                        (simpleDateFormat.format(datPhong.getNgayDat()).compareTo(checkOutDate) < 1 && simpleDateFormat.format(traPhong.getNgayTra()).compareTo(checkInDate) > -1)) {

                    checkDate = false;
                    break;
                }
        }
        return checkDate;
    }

    
    //CheckCodeQR
    @RequestMapping(value = "qrcode/{name}", method = RequestMethod.GET)
    public void qrcode(
            @PathVariable("name") String name,

            HttpServletResponse response) throws Exception {
        response.setContentType("image/png");
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(ZXingHelper.getQRCodeImage(name, 100, 100));
        outputStream.flush();
        outputStream.close();
    }

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Configuration configuration;

    
    //SendMail
    @RequestMapping(value = "/send")
    public String sendmail(@RequestParam("name") String name,
                           @RequestParam("to") String to,
                           @RequestParam("subject") String subject,
                           @RequestParam("content") String content) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setText(name);
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(content);
        javaMailSender.send(msg);

        return "redirect:contact";
    }


//Booking Paypal
    @RequestMapping(value = "/bookingroom", method = RequestMethod.POST)
    public String bookinginvoice(@Valid @ModelAttribute("bookingDTO") BookingDTO bookingDTO, Model model, WebRequest request, HttpSession session, ModelMap modelMap) throws ParseException {
        Room phong = quanLyPhongService.getByMaPhong(bookingDTO.getRoomCode());
        modelMap.put("paypalConfig", paypalServices.getPayPalConfig());
        if (phong == null) {
            if (!quanLyPhongService.findAllByLoaiPhongTenLoaiPhong(bookingDTO.getRoomType()).isEmpty()) {
                List<Room> phongs = quanLyPhongService.findAllByLoaiPhongTenLoaiPhong(bookingDTO.getRoomType());
                for (Room p : phongs) {
                    if (ittkhService.findAllByRoomMaPhongOrderByNgayDatDesc(p.getMaPhong()).isEmpty()) {
                        phong = p;
                        break;
                    } else {

                        if (checkDate(p, bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate())) {
                            phong = p;
                            break;
                        }
                    }
                }
            } else {
                model.addAttribute("error", "Out of room");
                return "bookingroom";
            }
        } else {
            if (!ittkhService.findAllByRoomMaPhongOrderByNgayDatDesc(phong.getMaPhong()).isEmpty()) {

                if (!checkDate(phong, bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate())) {
                    model.addAttribute("error", "Room has been reserved");
                    return "bookingroom";
                }

            }
        }

        if (phong == null) {
            model.addAttribute("error", "Out of room");
            return "bookingroom";
        }

        if (bookingDTO.getCheckInDate().compareToIgnoreCase(new SimpleDateFormat("yyyy-MM-dd").format(new Date())) < 0) {
            System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            //ở đây viết đúng nó hiện số chuẩn :v
            System.out.println(bookingDTO.getCheckInDate());
            model.addAttribute("error", "Check in date can greater or equal today");
            return "bookingroom";
        }


        if (bookingDTO.getCheckInDate().compareTo(bookingDTO.getCheckOutDate()) > -1) {
            model.addAttribute("error", "Check in date can't greater check out date");
            return "bookingroom";
        }

        java.sql.Date sqlDate = java.sql.Date.valueOf(String.valueOf(bookingDTO.getCheckInDate()));
        java.sql.Date sqlDate1 = java.sql.Date.valueOf(String.valueOf(bookingDTO.getCheckOutDate()));

        Checkin datPhong = new Checkin((int) (ittkhService.countfindAll() + 1),
                bookingDTO.getName(),
                bookingDTO.getPhoneNumber(),
                bookingDTO.getEmail(),
                phong,
                sqlDate
        );

        Checkout traPhong = new Checkout(iTraPhong.findAll().size() + 1,
                datPhong,
                sqlDate1,
                phong.getGiaPhong());

        session.setAttribute("bookingDTO", bookingDTO);
        session.setAttribute("datPhong", datPhong);
        session.setAttribute("traPhong", traPhong);
        session.setAttribute("phong", phong);
        model.addAttribute("bookingDTO", session.getAttribute("bookingDTO"));
        model.addAttribute("datPhong", session.getAttribute("datPhong"));
        model.addAttribute("traPhong", session.getAttribute("traPhong"));
        model.addAttribute("phong", session.getAttribute("phong"));
        return "invoice";
    }
    
    //Paypal
    @RequestMapping(value = "success",method = RequestMethod.GET)
	public String success(HttpServletRequest request, HttpSession session) {
		PayPalSucess payPalSucess = new PayPalSucess();
		PayPalResult payPalResult = payPalSucess.getPayPal(request,paypalServices.getPayPalConfig());
		
		Checkin datPhong = (Checkin)session.getAttribute("datPhong");
        Checkout traPhong = (Checkout)session.getAttribute("traPhong");

        ittkhService.save(datPhong);
        iTraPhong.save(traPhong);
		
		return "success";
	}

}
