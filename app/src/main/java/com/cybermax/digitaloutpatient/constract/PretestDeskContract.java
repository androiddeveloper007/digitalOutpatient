package com.cybermax.digitaloutpatient.constract;


import com.cybermax.digitaloutpatient.bean.Question;

import java.util.List;

public interface PretestDeskContract {

    interface View {

        void showQuestions(List<Question> questions);
    }
}
