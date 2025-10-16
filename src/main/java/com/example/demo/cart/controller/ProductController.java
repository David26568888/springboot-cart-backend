package com.example.demo.cart.controller;

import java.util.List;

import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.cart.exception.AddException;
import com.example.demo.cart.exception.ProductNotFoundException;
import com.example.demo.cart.model.dto.ProductDTO;
import com.example.demo.cart.response.ApiResponse;
import com.example.demo.cart.service.ProductService;

/*ProductController:
Request Mapping: "/products"
---------------------------------------------------------------------------------------------------
GET  "", "/"   查詢所有商品(多筆) 範例: "/products"   或 "/products/"
GET  "/{id}"   查詢指定商品(單筆) 範例: "/products/1" 或 "/products/2"
POST "", "/"   新增商品        範例: "/products"   或 "/products/"
*/
/*
 HTTP錯誤代碼
 https://zh.wikipedia.org/zh-tw/HTTP%E7%8A%B6%E6%80%81%E7%A0%81
 * */



@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:5173") //跨域存取
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping(value = {"", "/"})
	public  ApiResponse<List<ProductDTO>> getAllProducts(){
		List<ProductDTO> productDTOs = productService.getAllProducts();
		return new ApiResponse<>(200,"查詢成功",productDTOs);
	}
	
	@GetMapping("/{id}")
	public ApiResponse<ProductDTO> getProdut(@PathVariable(name ="id") Long productId){
		try {
			ProductDTO productDTO =  productService.getProductById(productId);
			return new ApiResponse<>(200,"查詢成功",productDTO);
		} catch (ProductNotFoundException e) {
			return new ApiResponse<>(404,e.getMessage(),null);
		}
	}
	
	@PostMapping(value = {"", "/"})
	// 會自動將 client 上傳的 json 轉成 ProductDTO
	public ApiResponse<Object> addProduct(@RequestBody ProductDTO productDTO) {
		try {
			ProductDTO savedProductDTO = productService.saveProduct(productDTO);
			return new ApiResponse<>(HttpStatus.OK.value(), "新增成功", savedProductDTO);
		} catch (AddException e) {
			//return new ApiResponse<>(400, e.getMessage(), null);
			return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
		} catch(Exception e2) {
			return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "其他錯誤"+e2.getMessage(), null);
		}
	}
}
