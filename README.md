

https://github.com/user-attachments/assets/45788a47-53a7-4dd3-a67a-8ffb420d7f0d

Приложение Мини тв

Цель:
Приложение, в котором показывается видео по очереди, т.е. при завершении текущего видео, стартует следующее и так циклично, постоянно повторяя, показывает видео.
При каждом старте видео ролика сохранить отчет о нем в базу данных.

Условия:

Шаг 1: найти 10 разных видео, создать папку /Videos в папку asset и скопировать все видео в эту папку.

Шаг 2:
Создать медиалист в виде json файле в папку asset (название medialist.json)
[
{
"VideoId": 1,
"VideoIdentifier": "video1.mp4",
"OrderNumber": 1
},
{
"VideoId": 2,
"VideoIdentifier": "video2.mp4",
"OrderNumber": 2
},
{
"VideoId": 3,
"VideoIdentifier": "video3.mp4",
"OrderNumber": 3
},

...

{
"VideoId": 10,
"VideoIdentifier": "video10.mp4",
"OrderNumber": 10
}
]

и используя GSON привести json to object (на data class с котлина)

Шаг 3: Создать базу данных с названием minitv.db и таблицу reports с полями id int auto_increment, id_video int, video_name string и startTime date. Тут при каждом старте видео записать отчет о видео. Характеристики видео можно взять с медиалиста.

Использовать для этого приложения:
1. Паттерн MVVM
2. Room
3. Kotlin coroutines (желательно viewmodelScope из viewmodel)
4. Livedata or kotlin flow
5. GSON
6. SQLITE
7. Паттерн Repository
8. Для показа видео использовать SurfaceView
9. Остальное на Ваше усмотрение.

Тестовое задание необходимо делать на языке Kotlin.
Результат выложить на Github.com.
