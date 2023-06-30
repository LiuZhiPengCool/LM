package com.lzp.librarymanagament.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzp.librarymanagament.dto.APDTO;
import com.lzp.librarymanagament.dto.ArticalDTO;
import com.lzp.librarymanagament.entity.Artical;
import com.lzp.librarymanagament.entity.Book;
import com.lzp.librarymanagament.entity.BookUser;
import com.lzp.librarymanagament.mapper.ArticalMapper;
import com.lzp.librarymanagament.mapper.BookUserMapper;
import com.lzp.librarymanagament.service.ArticalService;
import com.lzp.librarymanagament.util.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.lzp.librarymanagament.util.Constant.BOOK_LIMIT;

@Service
public class ArticalServiceImpl extends ServiceImpl<ArticalMapper, Artical> implements ArticalService {

    @Autowired
    ArticalMapper articalMapper;

    @Autowired
    BookUserMapper bookUserMapper;

    @Override
    public List<ArticalDTO> getAll(Integer page) {
        List<Artical> articals = list();
        List<ArticalDTO> reverse = new ArrayList<>();

        for (Artical artical : articals) {
            Integer id = artical.getUserId();

            BookUser user = bookUserMapper.selectOne(new QueryWrapper<BookUser>().eq("id", id));
            ArticalDTO ad = new ArticalDTO();
            ad.setHeader(artical.getHeader());
            ad.setTime(artical.getTime());
            ad.setText(artical.getText());
            ad.setName(user.getPhone());
            ad.setPush(artical.getPush());
            reverse.add(ad);
        }

        // 1(1~5,0~4) 2(6~10,5~9)
        Deque<ArticalDTO> res = new ArrayDeque<>();

        for(int i = (int) ((page - 1) * BOOK_LIMIT); i <= page * BOOK_LIMIT - 1 && i < reverse.size(); i++) {
            res.addLast(reverse.get(i));
        }
        return new ArrayList<>(res);
    }

    @Override
    public String push(APDTO ap) {
        QueryWrapper<Artical> header = new QueryWrapper<Artical>().eq("header", ap.getHeader());
        Artical artical = articalMapper.selectOne(header);
        UpdateWrapper<Artical> uw = new UpdateWrapper<Artical>().eq("header", ap.getHeader());
        artical.setPush("推送");
        articalMapper.update(artical, uw);
        return "成功";
    }

    public String backpush(APDTO ap) {
        QueryWrapper<Artical> header = new QueryWrapper<Artical>().eq("header", ap.getHeader());
        Artical artical = articalMapper.selectOne(header);
        UpdateWrapper<Artical> uw = new UpdateWrapper<Artical>().eq("header", ap.getHeader());
        artical.setPush("未推送");
        articalMapper.update(artical, uw);
        return "成功";
    }

    @Override
    public List<Artical> getAllArtical() {
        return list();
    }

    @Override
    public void saveArtical(ArticalDTO ap) {
        String phone = Login.userDTO.getPhone();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(date);
        BookUser user = bookUserMapper.selectOne(new QueryWrapper<BookUser>().eq("phone", phone));
        Artical artical = new Artical(1L, Math.toIntExact(user.getId()), ap.getHeader(), ap.getText(), format, "未推送");
        articalMapper.insert(artical);
    }

    @Override
    public Artical getArticalDetails() {
        return articalMapper.selectOne(new QueryWrapper<Artical>().eq("header", Login.articalDTO.getHeader()));
    }

    @Override
    public void del(ArticalDTO ap) {
        articalMapper.delete(new QueryWrapper<Artical>().eq("header", ap.getHeader()));
    }
}
