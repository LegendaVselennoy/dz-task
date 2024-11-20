Разработка Системы Управления Задачами

Запуск проекта на локальной машине.
Установите локально проект из Github, командой git clone <репозиторий>

Требования:

JDK версии 8 и выше.
Интегрированная среда разработки (IDE) — например, IntelliJ IDEA или Eclipse.
Maven или Gradle для управления зависимостями.

Запуск сервера:

В вашей IDE откройте файл конфигурации сервера (например, application.properties или application.yml).
Задайте свойства подключения к базе данных и другие необходимые параметры.
Запустите сервер:
В IntelliJ IDEA перейдите в меню «Run» → «Edit Configurations...».
Выберите конфигурацию сервера и нажмите кнопку «OK».
Сервер запустится, и вы сможете увидеть логи в консоли.

Для разработки и тестирования вы можете использовать инструменты, такие как Postman или curl, 
которые предоставляют удобную среду для отправки HTTP-запросов.
При работе с данными рекомендуется использовать базы данных, 
такие как PostgreSQL или MySQL, для хранения информации о задачах.

Программа представляет собой простое веб-приложение Spring Boot / Spring Framework.
Система должна обеспечивать создание, редактирование, удаление и просмотр задач. 
Каждая задача содержит заголовок, описание, статус (например, "в ожидании", "в процессе", "завершено"),
приоритет (например, "высокий", "средний", "низкий") и комментарии, а также автора задачи и исполнителя.


Функциональные требования:

Создание задач (saveNewTask()): система должна предоставлять возможность создания новых задач с указанием заголовка, 
описания, статуса, приоритета и комментариев. При создании задачи автор автоматически добавляется в колонку author.

Частичное редактирование задач, указывая id задачи и executor (patchTask()):
пользователи должны иметь возможность редактировать уже созданные задачи, 
изменяя статус, комментарии по исполнителю. Задачу другого пользователя изменить не удаться.

Получение списка задач по исполнителю (getTaskByExecutor()): пользователь может получить список своих задач, при
поддержке пагинации и фильтрации по executor.

Просмотр всех задач (getTasksALl()): выводит список задач всех пользователей.

Удаление задач (deleteTask()): пользователи должны иметь возможность удалять ненужные или завершённые задачи.

Изменение задачи (updateTask()): предоставляет возможность полностью изменить задачу.

Безопасность: система должна обеспечивать защиту данных от несанкционированного доступа и изменения.

Регистрация пользователя(createCustomer()): регистрирует пользователя.

Получить список всех пользователей (getAllCustomers()): возвращает всех пользователей.

Описан API с помощью Open API и Swagger.