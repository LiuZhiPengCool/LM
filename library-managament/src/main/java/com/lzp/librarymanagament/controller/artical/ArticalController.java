package com.lzp.librarymanagament.controller.artical;

import com.lzp.librarymanagament.dto.APDTO;
import com.lzp.librarymanagament.dto.ArticalDTO;
import com.lzp.librarymanagament.dto.Result;
import com.lzp.librarymanagament.entity.Artical;
import com.lzp.librarymanagament.service.ArticalService;
import com.lzp.librarymanagament.util.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/artical")
public class ArticalController {

    @Autowired
    private ArticalService articalService;

    @GetMapping("/all")
    @ResponseBody
    public Result getAllArtical() {
        List<Artical> articalList = articalService.getAllArtical();
        return Result.ok(articalList);
    }

    @GetMapping("/all/{page}")
    @ResponseBody
    public Result getAllArtical(@PathVariable("page") Integer page) {
        List<ArticalDTO> articalList = articalService.getAll(page);
        return Result.ok(articalList);
    }

    @RequestMapping("/push")
    public Result pushArtical(@RequestBody APDTO ap) {
        String str = articalService.push(ap);
        return Result.ok(str);
    }

    @RequestMapping("/backpush")
    public Result backpush(@RequestBody APDTO ap) {
        String str = articalService.backpush(ap);
        return Result.ok(str);
    }

    @PostMapping("/save")
    @ResponseBody
    public Result saveartical(@RequestBody ArticalDTO ap) {
        articalService.saveArtical(ap);
        return Result.ok();
    }

    @PostMapping("/news")
    @ResponseBody
    public Result news(@RequestBody ArticalDTO ap) {
        Login.articalDTO = ap;
        return Result.ok();
    }

    // 新闻界面详情内容
    @GetMapping("/details")
    @ResponseBody
    public Result getArticalDetails() {
        Artical artical = articalService.getArticalDetails();
        return Result.ok(artical);
    }

    // 删除文章
    @PostMapping("/del")
    @ResponseBody
    public Result del(@RequestBody ArticalDTO ap) {
        articalService.del(ap);
        return Result.ok();
    }
}
