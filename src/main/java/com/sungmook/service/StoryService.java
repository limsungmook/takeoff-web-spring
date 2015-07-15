package com.sungmook.service;

import com.sungmook.domain.ContentReadUser;
import com.sungmook.domain.Story;
import com.sungmook.repository.ContentReadUserRepository;
import com.sungmook.repository.MemberRepository;
import com.sungmook.repository.StoryRepository;
import com.sungmook.security.GetCurrentUser;
import com.sungmook.security.SessionUser;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Date;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Service
public class StoryService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private ContentReadUserRepository contentReadUserRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public void save(Story story){
        storyRepository.save(story);
    }

    @Transactional
    public Story readAndFindById(Long id) {
        Story story = storyRepository.findOne(id);
        SessionUser sessionUser = GetCurrentUser.get();

        // 로그인한 사용자가 존재하지 않을 때는 지금의 브라우저에서 하루에 한 번만 조회할 수 있는 기능으로 세션을 이용한다.
        if( sessionUser == null ){
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession httpSession = attr.getRequest().getSession(true); // true == allow create
            String contentReadSessionKey = "content-read-" + id;
            Date lastReadDate = (Date) httpSession.getAttribute(contentReadSessionKey);
            if( lastReadDate == null || new DateTime(lastReadDate).plusHours(24).isBeforeNow() ){
                httpSession.setAttribute(contentReadSessionKey, new Date());

                story.setReadCount( story.getReadCount() + 1 );
            }
        }

        // 로그인한 사용자가 존재할 때는 ContentReadUser 엔티티를 뒤져서 만약 존재하면 pass 하고 존재하지 않으면 조회수를 증가한다.
        if( sessionUser != null && !(story.getMember().getId().equals(sessionUser.getMemberId()))){
            ContentReadUser contentReadUser = contentReadUserRepository.findOneByContentIdAndMemberId(id, sessionUser.getMemberId());
            if( contentReadUser == null ){
                contentReadUser = new ContentReadUser();
                contentReadUser.setContent(story);
                contentReadUserRepository.save(contentReadUser);

                story.setReadCount( story.getReadCount() + 1 );
            }
        }
        return story;
    }
}
