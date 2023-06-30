package com.lzp.librarymanagament.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzp.librarymanagament.dto.APDTO;
import com.lzp.librarymanagament.dto.ArticalDTO;
import com.lzp.librarymanagament.entity.Artical;

import java.util.List;

public interface ArticalService extends IService<Artical> {
    List<ArticalDTO> getAll(Integer page);

    String push(APDTO ap);
    String backpush(APDTO ap);

    List<Artical> getAllArtical();

    void saveArtical(ArticalDTO ap);

    Artical getArticalDetails();

    void del(ArticalDTO ap);
}
