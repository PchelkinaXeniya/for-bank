require:requirements.sc

theme: /

    state: Start
        q!: $regex</start>
        a: Добрый день! Я виртуальный помощник банка «Открытие»!<br/>Чем могу помочь?


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