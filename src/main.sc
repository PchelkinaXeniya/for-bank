require:requirements.sc

theme: /

    state: Start
        q!: $regex</start>
        a: Добрый день! Я виртуальный помощник банка «Открытие»!<br/>Чем могу помочь?



state: questions
            a: У вас остались вопросы?
            
            state: yes
                q: * $yes *
                go!: /areYouReadyToOrderCard/questions/operator
            state: no
                q: * $no *
                go!: /areYouReadyToOrderCard/questions/thanksForContacting
            
            state: operator
                a: Соединяю с оператором

            #ШАГ18     
            state: thanksForContacting
                a: Благодарим за обращение в банк «Рога и Копыта»!
                script:
                    $jsapi.stopSession();
            
    state: NoMatch || noContext = true
        event!: noMatch
        random:
            a: Не смог разобрать :( Попробуете сказать иначе?
            a: Простите, я не понял вас. Давайте попробуем еще раз?
            a: Извините, я не понял. Попробуйте сформулировать по-другому                