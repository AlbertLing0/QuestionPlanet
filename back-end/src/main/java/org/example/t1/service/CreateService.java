package org.example.t1.service;

/**
 * @author 郑悦
 * @Description:
 * @date 2024/5/24 22:28
 */
public interface CreateService {
    //通过用户名新建问卷
    String createQuePaper(String username);
    //通过问卷id删除问卷
    String deleteQuePaper(Integer quePaperId);
    //获取问卷的问题列表
    String getQuestionList(Integer quePaperId);
    //保存问卷标题内容等
    String saveQuePaperOutline(String quePaper);
    //保存问卷(带问题)
    String saveQuePaper(String quePaper, String questionList);
    //获取问卷标题内容等
    String getQuePaperOutline(Integer quePaperId);
    //保存问题
    String saveOneQuestion(String question, Integer quePaperId);
    //删除问卷
    String releaseQuePaper(Integer quePaperId);
    //关闭问卷
    String closeQuePaper(Integer quePaperId);
}
