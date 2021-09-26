# GithubRepositoriesApp
Приложение,способное найти любого пользователя гитхаба по его имени,отобразить все его репозитории, а также скачать понравившийся репозиторий

Приложение сделано с помощью api github на базе чистой архитектуры (**Clean Architecture**) cсоблюдением приципов ООП, а также SOLID <br /> <br />
Для всех необходимых классов были написаны unit-тесты. Написаны user story и test cases

Историю создания проекта можно посмотреть по коммитам либо по веткам

**1. Маппинг данных через слои** <br />
**2. Архитектурный паттерн MVVM** <br />
**3. Для работы с сетью я использовал Retrofit совместно с RxJava** <br />
**4. В качестве локального хранилища была использована библиотека  Room** <br />
**5. Glide для загрузки и кеширования иконки юзера** <br />
**6. Паттерн "Service locator" в качестве предоставления зависимостей классам**

### Сейчас я хочу кратко рассказать о каждой фиче данного приложения (будет приложено видео,как что собой представляет та или иная фича)

#### Первая фича - "GA01_users" (получение юзера гитхаба по сети черех Retrofit)
https://www.youtube.com/watch?v=oexxw_UGW_o <br/>

#### Вторая фича - "GA02_local_users" (получение юзеров,закешированных раннее) <br/>
После получения юзера из сети мы сохраняли его локально (Room),теперь мы можем использовать наше приложение без интернета
https://www.youtube.com/watch?v=K-KNFNeaX2I <br/>

#### Третья и четвертая фичи - "GA03,GA04 repositories" (получение репозиториев юзера гитхаба,кеширование их) <br/>
Мы можем получать репозитории юзера,нажав на самого юзера. В эти фичи я обЪединил получение репозиториев по сети,а также сохранение их локально <br />
Используем функционал приложения независимо от интернета
https://www.youtube.com/watch?v=eQygn-4TZb4 <br/>

#### Пятая фича приложение - "GA05_collapse_item" (свертывание и развертывание элементов списка)
Теперь мы можем разворачивать элемент списка для получения дополнительной информации. В случае элемента юзера - это его биография (если она присутствует), 
в случае элемента репозиторий - его основная ветка

Стоит отметить,что для состояния элементов я кеширую класс,который инициализируется один раз при создании фрагмента,что позволяет не делать каждый раз запрос в базу данных,а обращаться к нашему классу и брать элементы списка по состоянию
Данный класс можно анйти по следующему пути : ui -> core -> cache -> UiTempCache
Также был реализован searchview в тулбаре приложения
https://www.youtube.com/watch?v=g3i577UKTRI <br/>

#### Следующая фича (шестая по счету) - "GA06_filter_items" (отображение элементов списка по интересующему состоянию)
Всего 3 состояния списка: collapsed(отображать лишь свернутые), expanded(отображать лишь развернутые), any(отображать как сернутые,так и развернутые элементы списка)

Здесь хочется остановиться на поиске пользователя или репозиторий по названию
Был покрыт следующий случай: Условно,у нас есть пользователь гитхаба с ником "Bob",состояние его элемента - Collpased
Если мы в меню выбираем "Collapsed",то будут отображаться только "Collapsed" элементы,при поиске "Bob" успешно отобразится наш юзер

Если мы в меню выбираем "Expanded",то будут отображаться только "Expanded" элементы,при поиске "Bob" отобразится сообщение с ошибкой "Not found data by state",что означает не найден "Bob" с состоянием Expanded,но он есть в списке другого состояния

Чтобы сохранять выбранное состояние я использовал **Shared preferences**
https://www.youtube.com/watch?v=n8Ny8fQaRJk <br/>

#### Самая интересная фича под номером 7 - "GA07_download_repository"
Теперь наш пользователь может не только искать юзеров,просматривать его репозитории,фильтровать их по состоянию,но и скачивать любой репозиторий выбранного юзера
Стоит отметить,что в этой фиче я обрабатывал несколько состояний по нажатии на репозиторий <br /> <br />
Первое состояние - Big (файл слишком большой),когда в ui слой приходит данный класс,то мы отобажаем SnackBar(в котором говорим,что файл большой и в котором отображаем размер файла в килобайтах) с действитим "Download"
Если пользователь в течение 10 секунд нажимает на "Download",то начинается загрузка репозитория в память устройства,отображется диалоговое окно с процессом загрузки репозитоия<br /><br />
Второе состояние - WaitingToDownload,если размер файла небольшой,то в ui слой придет данный класс,после чего мы начнем загрузку репозитория в память устройства,отображая диалоговое окно с процессом загрузки<br /><br />
Третье состояние - Exist(данный репозиторий уже скачан)<br /><br />
Четвертое состояние - Failure,который инкапсулирует в себе сообщение об ошибке

Как выглядит фича номер 7 - https://www.youtube.com/watch?v=F2j8kRa1G5I

**P.s Очень надеюсь,что вы посомтрите на код**
