
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


## English

The apllication uses an [API github](https://docs.github.com/en/rest)<br />
Data layer was covered by [Junit - tests](https://www.tutorialspoint.com/junit/junit_test_framework.htm)<br />

You can see all the history of developming this project [here](https://github.com/KostyaGig/GithubRepositoriesApp/commits/master)<br />

Main stack<br />
**1. Map the data through layers (data,domain,presentation)** <br />
**2. MVVM architecture pattern** <br />
**3. [Retrofit](https://square.github.io/retrofit/) for working with network** <br />
**4. [RxJava](https://github.com/ReactiveX/RxAndroid) for asynchronous work** <br />
**5. [Room](https://developer.android.com/training/data-storage/room) for local store the data**<br />
**6. [Glide](https://github.com/bumptech/glide) for download images by url** <br />
**7. [Service locator pattern](https://en.wikipedia.org/wiki/Service_locator_pattern) for providing dependencies**<br />
**8. Clean architecture**

### A short description features of the application<br/>

#### The first feature is "GA01_users"<br/>
Fetching a github user by his name from the Internet and show a detail information about him using [Retrofit](https://square.github.io/retrofit/)<br/>

#### The second feature is "GA02_local_users". <br/>
Fetching the github user by his name for the local storage using [Room](https://developer.android.com/training/data-storage/room) <br/>

#### The third "GA03 repositories" and fourth "GA04 repositories" features was included together.<br/>
Fething the repositories clicked github user from the Internet and show him in the recycler view. The fetched data about the user will be cached with the help of [Room](https://developer.android.com/training/data-storage/room) <br/>

#### The fifth feature is "GA05_collapse_item" Collapsed and Expanded recycler view's items<br/>
It Was implemented an opportunity to see the user's detail information and also hide it it<br/>
When the item of the recycler view is an collapsed state then we see a short bio about a github user<br/>
When the item has an expanded state we cane see a detail bio about the user<br/>

#### The follow feature is "GA06_filter_items" filter the recycler view's items by collapsed and expanded<br/>

#### The last feature is "GA07_download_repository"<br/>
Using this application our users have an opportunity to download other github user's repositories for the following research their code<br/>
They'll be able to learn code other userds absolutely free and offline!<br/>




