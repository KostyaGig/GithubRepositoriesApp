
# GithubRepositoriesApp

Приложение,способное найти любого пользователя гитхаба по его имени,отобразить все его репозитории, а также скачать понравившийся репозиторий

GuithubRepositoriesApp be able to find anything github user by his name and show all his repositories

Приложение сделано с помощью api github на базе чистой архитектуры (**Clean Architecture**) cоблюдая принципы ООП,а также SOLID <br />

Для всех необходимых классов были написаны unit-тесты. Написаны user story и test cases

Историю создания проекта можно посмотреть по коммитам либо по веткам

**1. Маппинг данных через слои** <br />
**2. Архитектурный паттерн MVVM** <br />
**3. Retrofit для запросов в сеть** <br />
**4. RxJava для асинхронной работы** <br />
**5. Room для локального хранения данных**<br />
**6. Glide для загрузки и кеширования иконки юзера** <br />
**7. Паттерн "Service locator" для предоставления зависимостей извне**

### Сейчас я хочу кратко рассказать о каждой фиче данного приложения (будет приложено видео,как что собой представляет та или иная фича)

#### Первая фича - "GA01_users" (получение юзера гитхаба по сети черех Retrofit)

![](https://media.giphy.com/media/D7nrS78gNIQhJcynVc/giphy.gif)

#### Вторая фича - "GA02_local_users" (получение юзеров,закешированных раннее) <br/>
Получая юзера из сети мы сохраняли его локально. Теперь мы имеем возможность видеть список юзеров,раннее полученных с интернета
<br/>

![](https://media.giphy.com/media/ODUATSfpF8dgFFjUn3/giphy.gif)

#### Третья и четвертая фичи - "GA03,GA04 repositories" (получение репозиториев юзера гитхаба,кеширование их) <br/>
В первый раз по нажатии на юзера его репозитории сохраняются локально,благодаря чему мы можем просматривать список юзера автономно
<br/>
![](https://media.giphy.com/media/HGcHNPd3GmF2WtLoZU/giphy.gif)

#### Пятая фича приложение - "GA05_collapse_item" (свертывание и развертывание элементов списка)
Теперь мы можем разворачивать элемент списка для получения дополнительной информации. В случае элемента юзера - это его биография (если она присутствует), 
в случае элемента репозиторий - его основная ветка

Users        |  Repositories
:-------------------------:|:-------------------------:
![](https://media.giphy.com/media/DaQuFY23Mhj9oEojtp/giphy.gif)  |  ![](https://media.giphy.com/media/F8lEf24rAmAQJiKI9g/giphy.gif)

Чтобы каждый раз не отправлять запрос в базу данных я кеширую состояния элементов в класс,что позволяет напрямую обращаться к этому классу. В нем инкапсулировано три листа,в каждом из которых хранятся элементы списка определенного состояния
Более подробно: ui -> core -> cache -> UiTempCache
Также был реализован searchview в тулбаре приложения  <br/>

#### Следующая фича (шестая по счету) - "GA06_filter_items" (отображение элементов списка по интересующему состоянию)
Всего 3 состояния списка: collapsed(отображать лишь свернутые), expanded(отображать лишь развернутые), any(отображать как сернутые,так и развернутые элементы списка)

Здесь хочется остановиться на поиске пользователя или репозиторий по названию
Был покрыт следующий случай: Условно,у нас есть пользователь гитхаба с ником "Bob",состояние его элемента - Collpased
Если мы в меню выбираем "Collapsed",то будут отображаться только "Collapsed" элементы,при поиске "Bob" успешно отобразится элемент списка с ником юзера "Bob"

Если мы в меню выбираем "Expanded",то будут отображаться только "Expanded" элементы,при поиске "Bob" отобразится сообщение с ошибкой "Not found data by state",что означает не найден юзер с ником "Bob", и с состоянием Expanded,но данный элемент списка с таким юзером находится в другом состоянии <br/>

![](https://media.giphy.com/media/KIxdVvyKRegKmU5suU/giphy.gif) 


Чтобы сохранять выбранное состояние я использовал **Shared preferences**<br/>
![](https://media.giphy.com/media/vKxgiipY6SvhjuFQ9c/giphy.gif) 

#### Самая интересная фича под номером 7 - "GA07_download_repository"
Теперь наш пользователь может не только искать юзеров,просматривать его репозитории,фильтровать их по состоянию,но и скачивать любой репозиторий выбранного юзера
Стоит отметить,что в этой фиче я обрабатывал несколько состояний по нажатии на репозиторий <br /> <br />
Первое состояние - Big (файл слишком большой),когда в ui слой приходит данное состояние,мы отобажаем SnackBar с действитим "Download"
В snackbar говорится о том,что размер репозитория большой.К тому же отображается размер нажатого репозитория в килобайтах.
Если пользователь в течение 10 секунд нажимает на "Download",то начинается загрузка репозитория в память устройства,отображется диалоговое окно с процессом загрузки.
Второе состояние - WaitingToDownload,когда размер нажатого репозитория небольшой,то в ui слой приходит это состояние,после чего мы начнем загрузку репозитория в память устройства,отображая диалоговое окно с процессом загрузки <br /><br />
Третье состояние - Exist(данный репозиторий уже скачан)<br /><br />
Четвертое состояние - Failure,который инкапсулирует в себе сообщение об ошибке

Поведение ui слоя в зависимости от пришедшего состояния вы можете лицезреть ниже



Big   |  WaitingToDownload | Exist | Failure
:-------------------------:|:-------------------------:|:-------------------------:|:-------------------------:
![](https://media.giphy.com/media/kGPaPmnyCGVJZFKx88/giphy.gif)  |  ![](https://media.giphy.com/media/XTPBx1b2WGTXpKvW8w/giphy.gif) |  ![](https://media.giphy.com/media/j30zaaM0oOFNUvHEHg/giphy.gif) |  ![](https://media.giphy.com/media/CmG7gzHuyC1TQ0ANpE/giphy.gif)


## The Enlgish description

The apllication use an [API github](https://docs.github.com/en/rest)<br />
All the important kotlin classes coveraged [Junit - tests](https://www.tutorialspoint.com/junit/junit_test_framework.htm)<br />

The history of creation features the application we be able to see [here](https://github.com/KostyaGig/GithubRepositoriesApp/commits/master)<br />

Main stack<br />
**1. Map the data through layers (data,domain,presentation)** <br />
**2. MVVM is android architecture patter** <br />
**3. Retrofit for network requests** <br />
**4. RxJava for asynchronous work** <br />
**5. Room for local store the data**<br />
**6. Glide for download image by url** <br />
**7. The pattern Service locator for provide the dependencies**
**8. Clean architecture**

### The short description every feature of the application<br/>

#### The first feature is "GA01_users"<br/>
Fetching the github user by his name from the Internet and showing the infromation about him using recycler view<br/>

#### The second feature is "GA02_local_users". <br/>
Fetching the github user by his name for the local storage using Room<br/>

#### The third "GA03 repositories" and fourth "GA04 repositories" features was included together.<br/>
Fething the repositories clicked github user from the Internet and showing its using recycler view. Fetched github user's repositories caching into the Room<br/>
The following fething github user's repositories will read from the local storage 

#### The fifth feature is "GA05_collapse_item" Collapsed and Expanded recycler view's items<br/>
Was implement the opportunity to showing the user's detail information and hiding it<br/>
When the item of recycler view has a collapsed state then we see the little information about a github user<br/>
When the item has an expanded state then we be able to see github user's detail infromation like a bio<br/>

#### The follow feature is "GA06_filter_items" filter recycler view's items by collapsed and expanded<br/>

#### The last feature is "GA07_download_repository"<br/>
GithubRepositoriesApp has an opportunity to download and saved the interesting repositories other users<br/>
The users of this application be able to save all interesting repositories into an internal memory of device and learn the code other users offline!<br/>




