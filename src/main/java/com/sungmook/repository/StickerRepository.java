package com.sungmook.repository;

import com.sungmook.domain.Sticker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
public interface StickerRepository extends JpaRepository<Sticker, Long> {

    List<Sticker> findByType(Sticker.Type type);
}
