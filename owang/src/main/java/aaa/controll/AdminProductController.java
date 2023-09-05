package aaa.controll;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import aaa.model.ProductDTO;
import aaa.service.ProductMapper;
import jakarta.annotation.Resource;

@Controller
@RequestMapping("admin_product")
public class AdminProductController {
	
	@Resource
	ProductMapper pm;
	
	@RequestMapping("/modify")
	String admin_product_modify(Model mm)  {
		List<ProductDTO> data= pm.list();
		mm.addAttribute("ProductDTO", new ProductDTO());
		mm.addAttribute("data", data);
		return "admin/product/modify";
	}
    @PostMapping("/insertProduct")
    public String addProduct(@ModelAttribute ProductDTO productDTO) {
        // 상품 추가 로직 구현 (Service나 Repository를 사용하여 데이터베이스에 저장)
        pm.insert(productDTO);
        return "redirect:/admin_product/modify"; // 목록 페이지로 리다이렉션
    }
    @RequestMapping("/deleteProduct")
    public String deleteProduct(@RequestParam("productCode") String productCode) {
        pm.delete(productCode);
        return "redirect:/admin_product/modify"; // 목록 페이지로 리다이렉션
    }

	@RequestMapping("/graph")
	String admin_product_graph()  {
		return "admin/product/graph";
	}
	
	@RequestMapping("/payment")
	String admin_product_payment()  {
		return "admin/product/payment";
	}
	
}
