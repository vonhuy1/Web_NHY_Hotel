package hotel.Controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hotel.Dto.AjaxChangeTimeTp;
import hotel.Model.Checkin;
import hotel.Model.CheckinCalendar;
import hotel.Model.Checkout;
import hotel.Model.HourlyParameters;
import hotel.Model.Room;
import hotel.Model.ServiceMenu;
import hotel.Services.ILoaiphongSercives;
import hotel.Services.ITraPhong;
import hotel.Services.Idv;
import hotel.Services.Ilsdtp;
import hotel.Services.Ittp;
import hotel.Services.LichDatPhongService;
import hotel.Services.ThongSoTheoGioService;

@Controller
@Transactional
public class DptpController {

	@Autowired
	Ilsdtp ilsdtp;

	@Autowired
	Idv idv;

	@Autowired
	Ittp ittp;

	@Autowired
	ITraPhong iTraPhong;

	@Autowired
	ILoaiphongSercives iLoaiphongSercives;

	@Autowired
	ThongSoTheoGioService thongSoTheoGioService;

	@Autowired
	LichDatPhongService lichDatPhongService;
	
	private void activemenu(ModelMap model) {
		// active trên menu
		model.addAttribute("activedptp", "active");

	}

	@ModelAttribute(name = "changeURL")
	public String changeURL() {
		return "dptp";
	}

	@RequestMapping("/dsdattruoc")
	public String dsdattruoc(ModelMap model) {
		activemenu(model);
		List<CheckinCalendar> danhsach = lichDatPhongService.tongdsdatlich();
		model.addAttribute("titlepage", "List of reservations");
		model.addAttribute("danhsach", danhsach);
		return "dslichdatphong/tongdslichdatphong";
	}
	
	@RequestMapping("/dptp")
	public String dptp(ModelMap model) {
		activemenu(model);

		List<Room> l = (List<Room>) ittp.findAllOrderByASC();
		model.addAttribute("l", l);

		List<Integer> ltang = new ArrayList<>();
		// kiem tra da ton tai chua
		for (int i = 0; i < l.size(); i++) {
			if (!ltang.contains(l.get(i).getTang())) {
				ltang.add(l.get(i).getTang());
			}
		}

		if (l.isEmpty()) {

			model.addAttribute("message", "There are no rooms");
		}
		model.addAttribute("ltang", ltang);
		model.addAttribute("titlepage", "Check-in / Check-out");
		model.addAttribute("activedptptong", "active-status");
		setTrangThai(model);
		return "dptp";
	}
	
	@RequestMapping("/dptppck")
	public String dptppck(ModelMap model) {
		activemenu(model);

		List<Room> l = (List<Room>) ittp.findFilterPCKOrderByASC();
		model.addAttribute("l", l);

		List<Integer> ltang = new ArrayList<>();
		// kiem tra da ton tai chua
		for (int i = 0; i < l.size(); i++) {
			if (!ltang.contains(l.get(i).getTang())) {
				ltang.add(l.get(i).getTang());
			}
		}

		if (l.isEmpty()) {

			model.addAttribute("message", "There are no rooms");
		}
		model.addAttribute("ltang", ltang);
		model.addAttribute("titlepage", "Check-in / Check-out");
		model.addAttribute("activedptppck", "active-status");
		setTrangThai(model);
		return "dptp";
	}
	
	@RequestMapping("/dptppt")
	public String dptppt(ModelMap model) {
		activemenu(model);

		List<Room> l = (List<Room>) ittp.findFilterPTOrderByASC();
		model.addAttribute("l", l);

		List<Integer> ltang = new ArrayList<>();
		// kiem tra da ton tai chua
		for (int i = 0; i < l.size(); i++) {
			if (!ltang.contains(l.get(i).getTang())) {
				ltang.add(l.get(i).getTang());
			}
		}

		if (l.isEmpty()) {

			model.addAttribute("message", "There are no rooms");
		}
		model.addAttribute("ltang", ltang);
		model.addAttribute("titlepage", "Check-in / Check-out");
		model.addAttribute("activedptppt", "active-status");
		setTrangThai(model);
		return "dptp";
	}
	
	@RequestMapping("/dptppdt")
	public String dptppdt(ModelMap model) {
		activemenu(model);

		List<Room> l = (List<Room>) ittp.findFilterPDTOrderByASC();
		model.addAttribute("l", l);

		List<Integer> ltang = new ArrayList<>();
		// kiem tra da ton tai chua
		for (int i = 0; i < l.size(); i++) {
			if (!ltang.contains(l.get(i).getTang())) {
				ltang.add(l.get(i).getTang());
			}
		}

		if (l.isEmpty()) {

			model.addAttribute("message", "There are no rooms");
		}
		model.addAttribute("ltang", ltang);
		model.addAttribute("titlepage", "Check-in / Check-out");
		model.addAttribute("activedptppdt", "active-status");
		setTrangThai(model);
		return "dptp";
	}

	public void setTrangThai(ModelMap model) {
		List<Integer> lDatPhong = lichDatPhongService.listLichDatPhongCount();
		model.addAttribute("phongCoKhach", ittp.countRoomCoKhach());
		model.addAttribute("phongTrong", ittp.countRoomTrong());
		model.addAttribute("phongDatTruoc", lDatPhong.size());
		model.addAttribute("tongPhong", ittp.countFindAll());
	}

	@RequestMapping("/actionclickdptp")
	public String actionclickdptp(ModelMap model, @ModelAttribute("datphong") Checkin datphong,
								  @ModelAttribute("traphong") Checkout traphong, @RequestParam("maPhong") int maPhong,
								  @RequestParam("trangThai") int trangThai, @RequestParam("soPhong") int soPhong, @RequestParam(name = "maDatPhong", required = false) Integer maDatPhong,
								  HttpServletRequest httpServletRequest) throws ParseException {
		activemenu(model);
		model.addAttribute("soPhong", soPhong);
		model.addAttribute("maPhong", maPhong);
		HttpSession session = httpServletRequest.getSession();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat timeFormat = new SimpleDateFormat("HH:mm");
		Calendar cal = Calendar.getInstance();
		model.addAttribute("ngayhientai", dateFormat.format(cal.getTime()));
		model.addAttribute("giohientai", timeFormat.format(cal.getTime()));
		model.addAttribute("ngayhientaii", cal.getTime());
		model.addAttribute("nguoidung", session.getAttribute("nguoidung").toString());

		// phong chưa có ai đặt 0
		if (trangThai == 0) {
			return ClickVoTrangDatPhong(model, maPhong, soPhong);
		} else if (trangThai == 1 || trangThai == 2) {
			return ClickVoTrangTraPhong(model, maPhong, soPhong, trangThai, cal, maDatPhong);
		}
		return null;
	}

	public String ClickVoTrangDatPhong(ModelMap model, int maPhong, int soPhong) {
		Room ttphong = ittp.findById(maPhong).get();
		model.addAttribute("thongtinphong", ttphong);
		model.addAttribute("titlepage", "Reservations number " + soPhong);
		return "dptp/dp";
	}

	public String ClickVoTrangTraPhong(ModelMap model, int maPhong, int soPhong, int trangThai, Calendar cal, Integer maDatPhongHomestay)
			throws ParseException {
		// tính ra ngày giờ thuê và trả kiểu string format yyyy-MM-dd HH:mm:ss
		SimpleDateFormat fmDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat fmTime = new SimpleDateFormat("HH:mm:ss");
		// phòng khách đã đặt và ở trạng thái là 1
		Integer maDatPhong = null;
		double tongTiendv = 0;
		double tongTiendp = 0;
		double tongTien = 0;
		int soGioThue = 0;
		Integer songaythue = 0;
		
		// nếu là homestay
		if  (maDatPhongHomestay != null) {
			maDatPhong = maDatPhongHomestay;
		} else {
			List<Checkin> ldp = ilsdtp.timphongchuatra(maPhong); // tìm ra đơn đặt phòng chưa trả phòng
			for (int i = 0; i < ldp.size(); i++) {
				if (ldp.get(i).getTraPhongs().isEmpty()) {
					maDatPhong = ldp.get(i).getMaDatPhong(); // đã tìm ra mã đặt phòng của đơn đặt phòng chưa trả
					break;
				}
			}
		}
		
		List<ServiceMenu> lddv = idv.datdichvu(maDatPhong); // tìm tất cả các dịch vụ đã đặt của đơn đặt phòng
		for (int i = 0; i < lddv.size(); i++) {
			tongTiendv += lddv.get(i).getSoLuong() * lddv.get(i).getDichVu().getGiaDichVu();
		}

		// tìm ra đơn đặt phòng
		Checkin getdatphong = ilsdtp.findById(maDatPhong).get();

		if (getdatphong.getLoaiDat().equals("theogio")) {
			// get ra thông số theo giờ
			List<HourlyParameters> lstThongSoTheoGio = (List<HourlyParameters>) thongSoTheoGioService.findAll();
			int gioDau = lstThongSoTheoGio.get(0).getBaoNhieuGioDau();
			int soGioChuyenThanhNgay = lstThongSoTheoGio.get(0).getSoGioChuyenThanhNgay();

			String dateStart = fmDate.format(getdatphong.getNgayDat());
			String timeStart = fmTime.format(getdatphong.getGioDat());

			Date dateNow = new Date();
			String dateEnd = fmDate.format(dateNow);
			String timeEnd = fmTime.format(dateNow);

			String start = dateStart + " " + timeStart;
			String end = dateEnd + " " + timeEnd;

			// tính ra số giờ đã thuê
			soGioThue = tinhRaSoGioThue(start, end);

			if (soGioThue <= 0) {
				soGioThue = 1;
			}

			// nếu số giờ nhỏ hơn thông số theo ngày
			if (soGioThue <= soGioChuyenThanhNgay) {
				// tính ra tổng tiền đặt phòng theo giờ đầu và giờ sau
				if (soGioThue <= gioDau) {
					tongTiendp = soGioThue * getdatphong.getPhong().getGiaPhongGioDau();
				} else {
					tongTiendp = (gioDau * getdatphong.getPhong().getGiaPhongGioDau())
							+ (soGioThue - gioDau) * getdatphong.getPhong().getGiaPhongGioSau();
				}
				// nhân với giảm giá
				tongTiendp *= ((100 - Double.parseDouble(getdatphong.getPhong().getKhuyenMai())) / 100);

				tongTien = tongTiendp + tongTiendv - getdatphong.getTienCoc();

				model.addAttribute("soGioThue", soGioThue);
			} else {
				// nếu số giờ lớn hơn thông số theo ngày thì chuyển thành thuê ngày
				getdatphong.setLoaiDat("theongay");
				ilsdtp.save(getdatphong);
				return "redirect:actionclickdptp?maPhong=" + maPhong + "&trangThai=" + trangThai + "&soPhong="
						+ soPhong;
			}
		} else if (getdatphong.getLoaiDat().equals("theongay")) {
			// tính ra số ngày đã thuê
			String strDateStart = fmDate.format(getdatphong.getNgayDat());
			Date dateNgay = fmDate.parse(strDateStart);
			dateNgay.setHours(getdatphong.getGioDat().getHours());
			dateNgay.setMinutes(getdatphong.getGioDat().getHours());

			long ngaydatru = cal.getTime().getTime() - dateNgay.getTime();
			long soGioThueTheoNgay = ngaydatru / (60 * 60 * 1000);
			double soNgay = Math.ceil(Double.parseDouble(String.valueOf(soGioThueTheoNgay)) / 24);
			songaythue = (int) soNgay; // đã tính ra số ngày thuê
			if (songaythue <= 0) {
				songaythue = 1;
			}
			tongTiendp = (songaythue * getdatphong.getPhong().getGiaPhong())
					* ((100 - Double.parseDouble(getdatphong.getPhong().getKhuyenMai())) / 100);

			tongTien = tongTiendp + tongTiendv - getdatphong.getTienCoc();
		} else if (getdatphong.getLoaiDat().equals("homestay")) {
			// tính ra số ngày đã thuê
			String strDateStart = fmDate.format(getdatphong.getNgayDat());
			Date dateNgay = fmDate.parse(strDateStart);
			dateNgay.setHours(getdatphong.getGioDat().getHours());
			dateNgay.setMinutes(getdatphong.getGioDat().getHours());

			long ngaydatru = cal.getTime().getTime() - dateNgay.getTime();
			long soGioThueTheoNgay = ngaydatru / (60 * 60 * 1000);
			double soNgay = Math.ceil(Double.parseDouble(String.valueOf(soGioThueTheoNgay)) / 24);
			songaythue = (int) soNgay; // đã tính ra số ngày thuê
			if (songaythue <= 0) {
				songaythue = 1;
			}
			tongTiendp = (songaythue * getdatphong.getPhong().getGiaHomestay())
					* ((100 - Double.parseDouble(getdatphong.getPhong().getKhuyenMai())) / 100);

			tongTien = tongTiendp + tongTiendv - getdatphong.getTienCoc();
		}

		model.addAttribute("sophong", soPhong);
		model.addAttribute("tiencoc", getdatphong.getTienCoc());
		model.addAttribute("getdatphong", getdatphong);
		model.addAttribute("titlepage", "Check out number " + soPhong);
		model.addAttribute("maDatPhong", maDatPhong);
		model.addAttribute("tongTien", tongTien);
		model.addAttribute("tongTiendv", tongTiendv);
		model.addAttribute("tongTiendp", tongTiendp);
		model.addAttribute("listdv", lddv);
		model.addAttribute("songaythue", songaythue);
		model.addAttribute("trangThai", trangThai);
		model.addAttribute("giamgia", Integer.parseInt(getdatphong.getPhong().getKhuyenMai()));
		return "dptp/tp";
	}

	// format input 2012-03-14 09:33:58
	public int tinhRaSoGioThue(String dateStart, String dateStop) {
		// Custom date format
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = null;
		Date d2 = null;
		try {
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);
		} catch (ParseException e) {
		}
		long diff = d2.getTime() - d1.getTime();
		// long soGiay = diff / 1000;
		long soPhut = diff / (60 * 1000);
		// long soGio = diff / (60 * 60 * 1000);
		double soGio = Double.parseDouble(String.valueOf(soPhut)) / 60.0d;
		long soGioThanhToan = Math.round(soGio);
		return Integer.parseInt(String.valueOf(soGioThanhToan));
	}

	@GetMapping("/changeTimeTraPhong")
	public @ResponseBody AjaxChangeTimeTp ClickVoTrangTraPhong(ModelMap model,
			@RequestParam(name = "maPhong") int maPhong, @RequestParam(name = "soPhong") int soPhong,
			@RequestParam(name = "trangThai") int trangThai, @RequestParam(name = "gioTra") String gioTra,
			@RequestParam(name = "ngayTra") String ngayTra) throws ParseException {
		// tính ra ngày giờ thuê và trả kiểu string format yyyy-MM-dd HH:mm:ss
		SimpleDateFormat fmDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat fmTime = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat fmTimeNotSecond = new SimpleDateFormat("HH:mm");
		AjaxChangeTimeTp ajaxChangeTimeTp = new AjaxChangeTimeTp();
		// phòng khách đã đặt và ở trạng thái là 1
		Integer maDatPhong = null;
		double tongTiendv = 0;
		double tongTiendp = 0;
		double tongTien = 0;
		int soGioThue = 0;
		Integer songaythue = 0;
		List<Checkin> ldp = ilsdtp.timphongchuatra(maPhong); // tìm ra đơn đặt phòng chưa trả phòng
		for (int i = 0; i < ldp.size(); i++) {
			if (ldp.get(i).getTraPhongs().isEmpty()) {
				maDatPhong = ldp.get(i).getMaDatPhong(); // đã tìm ra mã đặt phòng của đơn đặt phòng chưa trả
				break;
			}
		}

		List<ServiceMenu> lddv = idv.datdichvu(maDatPhong); // tìm tất cả các dịch vụ đã đặt của đơn đặt phòng
		for (int i = 0; i < lddv.size(); i++) {
			tongTiendv += lddv.get(i).getSoLuong() * lddv.get(i).getDichVu().getGiaDichVu();
		}

		// tìm ra đơn đặt phòng
		Checkin getdatphong = ilsdtp.findById(maDatPhong).get();
		String getLoaiDat = getdatphong.getLoaiDat();
		String dateStart = fmDate.format(getdatphong.getNgayDat());
		String timeStart = fmTime.format(getdatphong.getGioDat());
		Date getDate = fmDate.parse(ngayTra);
		Date getTime = fmTimeNotSecond.parse(gioTra);
		if (getLoaiDat.equals("theogio")) {
			// get ra thông số theo giờ
			List<HourlyParameters> lstThongSoTheoGio = (List<HourlyParameters>) thongSoTheoGioService.findAll();
			int gioDau = lstThongSoTheoGio.get(0).getBaoNhieuGioDau();
			int soGioChuyenThanhNgay = lstThongSoTheoGio.get(0).getSoGioChuyenThanhNgay();
			String dateEnd = fmDate.format(getDate);
			String timeEnd = fmTime.format(getTime);

			String start = dateStart + " " + timeStart;
			String end = dateEnd + " " + timeEnd;

			// tính ra số giờ đã thuê
			soGioThue = tinhRaSoGioThue(start, end);

			if (soGioThue <= 0) {
				soGioThue = 1;
			}

			// nếu số giờ nhỏ hơn thông số theo ngày
			if (soGioThue <= soGioChuyenThanhNgay) {
				// tính ra tổng tiền đặt phòng theo giờ đầu và giờ sau
				if (soGioThue <= gioDau) {
					tongTiendp = soGioThue * getdatphong.getPhong().getGiaPhongGioDau();
				} else {
					tongTiendp = (gioDau * getdatphong.getPhong().getGiaPhongGioDau())
							+ (soGioThue - gioDau) * getdatphong.getPhong().getGiaPhongGioSau();
				}
				// nhân với giảm giá
				tongTiendp *= ((100 - Double.parseDouble(getdatphong.getPhong().getKhuyenMai())) / 100);

				tongTien = tongTiendp + tongTiendv - getdatphong.getTienCoc();
				ajaxChangeTimeTp.setSoGioThue(soGioThue);
			} else {
				// nếu số giờ lớn hơn thông số theo ngày thì chuyển thành thuê ngày
				getLoaiDat = "theongay";

				// tính ra số ngày đã thuê
				String strDateStart = fmDate.format(getdatphong.getNgayDat());
				Date dateNgay = fmDate.parse(strDateStart);
				dateNgay.setHours(getdatphong.getGioDat().getHours());
				dateNgay.setMinutes(getdatphong.getGioDat().getMinutes());
				getDate.setHours(getTime.getHours());
				getDate.setMinutes(getTime.getMinutes());
				long ngaydatru = getDate.getTime() - dateNgay.getTime();
				long soGioThueTheoNgay = ngaydatru / (60 * 60 * 1000);
				double soNgay = Math.ceil(Double.parseDouble(String.valueOf(soGioThueTheoNgay)) / 24);
				songaythue = (int) soNgay; // đã tính ra số ngày thuê
				if (songaythue <= 0) {
					songaythue = 1;
				}
				tongTiendp = (songaythue * getdatphong.getPhong().getGiaPhong())
						* ((100 - Double.parseDouble(getdatphong.getPhong().getKhuyenMai())) / 100);

				tongTien = tongTiendp + tongTiendv - getdatphong.getTienCoc();
				ajaxChangeTimeTp.setSoNgayThue(songaythue);

			}
		} else if (getLoaiDat.equals("theongay")) {
			// tính ra số ngày đã thuê
			String strDateStart = fmDate.format(getdatphong.getNgayDat());
			Date dateNgay = fmDate.parse(strDateStart);
			dateNgay.setHours(getdatphong.getGioDat().getHours());
			dateNgay.setMinutes(getdatphong.getGioDat().getMinutes());
			getDate.setHours(getTime.getHours());
			getDate.setMinutes(getTime.getMinutes());
			long ngaydatru = getDate.getTime() - dateNgay.getTime();
			long soGioThueTheoNgay = ngaydatru / (60 * 60 * 1000);
			double soNgay = Math.ceil(Double.parseDouble(String.valueOf(soGioThueTheoNgay)) / 24);
			songaythue = (int) soNgay; // đã tính ra số ngày thuê
			if (songaythue <= 0) {
				songaythue = 1;
			}
			tongTiendp = (songaythue * getdatphong.getPhong().getGiaPhong())
					* ((100 - Double.parseDouble(getdatphong.getPhong().getKhuyenMai())) / 100);

			tongTien = tongTiendp + tongTiendv - getdatphong.getTienCoc();
			ajaxChangeTimeTp.setSoNgayThue(songaythue);
		} else if (getLoaiDat.equals("homestay")) {
			// tính ra số ngày đã thuê
			String strDateStart = fmDate.format(getdatphong.getNgayDat());
			Date dateNgay = fmDate.parse(strDateStart);
			dateNgay.setHours(getdatphong.getGioDat().getHours());
			dateNgay.setMinutes(getdatphong.getGioDat().getMinutes());
			getDate.setHours(getTime.getHours());
			getDate.setMinutes(getTime.getMinutes());
			long ngaydatru = getDate.getTime() - dateNgay.getTime();
			long soGioThueTheoNgay = ngaydatru / (60 * 60 * 1000);
			double soNgay = Math.ceil(Double.parseDouble(String.valueOf(soGioThueTheoNgay)) / 24);
			songaythue = (int) soNgay; // đã tính ra số ngày thuê
			if (songaythue <= 0) {
				songaythue = 1;
			}
			tongTiendp = (songaythue * getdatphong.getPhong().getGiaHomestay())
					* ((100 - Double.parseDouble(getdatphong.getPhong().getKhuyenMai())) / 100);

			tongTien = tongTiendp + tongTiendv - getdatphong.getTienCoc();
			ajaxChangeTimeTp.setSoNgayThue(songaythue);
		}
		ajaxChangeTimeTp.setTongTien(tongTien);
		ajaxChangeTimeTp.setTienPhong(tongTiendp);
		return ajaxChangeTimeTp;
	}

	@RequestMapping("/actiontraphong")
	public String actiontraphong(ModelMap model, @ModelAttribute("traphong") Checkout traphong,
			@RequestParam("maPhong") int maPhong, HttpServletRequest httpServletRequest) {
		activemenu(model);
		// update người thu tiền cho đơn đặt phòng
		HttpSession session = httpServletRequest.getSession();
		String nguoiDung = session.getAttribute("nguoidung").toString();
		traphong.setNguoiThuTien(nguoiDung);
		// lưu đơn trả phòng
		iTraPhong.save(traphong);

		// save lại trạng thái mã phòng khi trả
		Room p = ittp.findById(maPhong).get();
		if(p.getCountHomestay() != null && p.getCountHomestay() > 0) {
			p.setCountHomestay(p.getCountHomestay() - 1);
			if (p.getCountHomestay() == 0 ) {
				p.setTrangThai(0);	
			}
		} else {
			p.setTrangThai(0);
		}
		
		ittp.save(p);

		model.addAttribute("message", "Check-out was successful");
		return dptp(model);
	}

	@RequestMapping("/themddp")
	public String themddp(ModelMap model, @ModelAttribute("datphong") Checkin datphong) {
		activemenu(model);
		ilsdtp.save(datphong);
		Room p = ittp.findById(datphong.getPhong().getMaPhong()).get();
		p.setTrangThai(1);
		ittp.save(p);
		model.addAttribute("message", "Successful booking");
		return dptp(model);
	}
}
