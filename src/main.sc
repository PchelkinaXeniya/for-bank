require:requirements.sc

theme: /

    state: Start
        q!: $regex</start>
        a: Добрый день! Я виртуальный помощник банка «Открытие»!<br/>Чем могу помочь?

    state: HowToChangePassword
        q: * ($change * $password | $PIN) *
        a:  Сейчас расскажу порядок действий.<br/>
            Выберите, что именно планируете сделать:<br/>
            1. Поменять пароль для входа в приложение.<br/>
            2. Поменять PIN-код от карты.<br/>
            Пожалуйста, отправьте цифру, соответствующую вашему выбору.
        
        state: one
            q: * 1 *
            go!: /HowToChangeAppPassword
        state: two
            q: * 2 *
            go!: /HowToChangeCardPassword
            
    
        
    state: HowToChangeAppPassword
        q: * ($change * $password | $PIN) *
    
    state: HowToChangeCardPassword
        q: * ($change * $password | $PIN) *

            #ШАГ    
            state: thanksForContacting
                a: Благодарим за обращение в банк «Открытие»!
                script:
                    $jsapi.stopSession();
            
    state: NoMatch || noContext = true
        event!: noMatch
        random:
            a: Не смог разобрать :( Попробуете сказать иначе?
            a: Простите, я не понял вас. Давайте попробуем еще раз?
            a: Извините, я не понял. Попробуйте сформулировать по-другому                