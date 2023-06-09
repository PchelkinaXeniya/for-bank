require:requirements.sc

theme: /
    
    state: Start
        q!: $regex</start>
        a: Здравствуйте!
        script:
            $session = {}
            
    state: HowToChangePassword
        q!: * ($change * ($password | $PIN)) *
        q!: * ($password | $PIN) *
        q!: * (($password | $PIN) * $app * $card) *
        a:  Здравствуйте!<br/>
            Сейчас расскажу порядок действий.<br/>
            Выберите, что именно планируете сделать:<br/>
            1. Поменять пароль для входа в приложение.<br/>
            2. Поменять PIN-код от карты.<br/>
            Пожалуйста, отправьте цифру, соответствующую вашему выбору.
        script:
            $response.replies = $response.replies || [];
            $response.replies.push({
                "type": "timeout",
                "interval": 60,
                "targetState": "/TheEnd"
            });
        
        state: ChangeAppPassword
            q: * 1 *
            go!: /HowToChangeAppPassword
            
        state: ChangeCardPassword
            q: * 2 *
            go!: /HowToChangeCardPassword
    
    state: HowToChangeAppPassword
        q: * ($change * ($password | $PIN) * $app) *
        a: Смена пароля от приложения возможна несколькими способами:<br/>
           1. на экране "Профиль" выберите "Изменить код входа в приложение".<br/>
           2. введите SMS-код.<br/>
           3. придумайте новый код для входа в приложение и повторите его.<br/>
        script:
            $response.replies = $response.replies || [];
            $response.replies.push({
                "type": "timeout",
                "interval": 2,
                "targetState": "/HowToChangeAppPassword/AlternativeToChangeAppPassword"
            });
        
        state: AlternativeToChangeAppPassword   
            event!: AlternativeEvent
            a: Либо нажмите на кнопку "Выйти" на странице ввода пароля для входа в приложение.<br/>
               После чего нужно будет заново пройти регистрацию<br/>
               1. ввести полный номер карты (если оформляли ранее, иначе номер телефона и дату рождения.<br/>
               2. указать код из смс-код,<br/>
               3. придумать новый пароль для входа<br/>
            script:
                $response.replies = $response.replies || [];
                $response.replies.push({
                    "type": "timeout",
                    "interval": 2,
                    "targetState": "/ThanksForContacting"
                });
        
    state: HowToChangeCardPassword
        q: * ($change * ($password | $PIN) * $card) *        
        a: Это можно сделать в приложении:<br/>
           1. На экране "Мои деньги" в разделе "Карты" нажмите на нужную.<br/>
           2. Выберите вкладку "Настройки".<br/>
           3. Нажмите "Сменить пин-код".<br/>
           4. И введите комбинацию, удобную вам.<br/>
           5. Повторите ее.<br/>
        go!: /HowToChangeCardPassword/Done 
        
        state: Done       
            a: И все готово!<br/>
               Пин-код установлен, можно пользоваться.<br/>
            go!: /ThanksForContacting 
        
    
    state: ThanksForContacting       
        a: Приятно было пообщаться. Всегда готов помочь вам снова!
        go!: /TheEnd
    
    state: TheEnd
        script:
            $jsapi.stopSession();
        
    state: NoMatch
        event!: noMatch
        random:
            a: Не смог разобрать :( Попробуете сказать иначе?
            a: Простите, я не понял вас. Давайте попробуем еще раз?
            a: Извините, я не понял. Попробуйте сформулировать по-другому                