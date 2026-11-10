# Heavy Diesel (v1)

Дизельный двигатель для Create, который работает на дизеле из TFMG.
Minecraft 1.21.1 · NeoForge 21.1.x · Create 6.0.x · TFMG 1.3.2a-community.

## Как собрать
1. Нужны JDK 21 и интернет. Откройте папку в IntelliJ IDEA (импорт Gradle) или в терминале:
   - `./gradlew build` → готовый мод в `build/libs/heavydiesel-1.0.0.jar`
   - `./gradlew runClient` → запуск игры для проверки
2. Для `runClient` положите в `run/mods/` jar TFMG (tfmg-1.21.1-1.3.2a-community.jar). Для самой сборки TFMG не нужен.
3. Если Gradle не находит Create, откройте https://maven.createmod.net/com/simibubi/create/create-1.21.1/ и впишите точную версию в `create_version` (gradle.properties).

## Как работает
- Двигатель занимает 2 блока в длину (модель из вашего файла разрезана пополам). Ставится один предмет, вторая половина появляется сама.
- Вал на задней стороне. Топливо: вёдра или трубы, принимается всё из тега `heavydiesel:engine_fuel` (`tfmg:diesel` + `#c:diesel`).
- Настройки в `DieselEngineBlockEntity.java`: обороты (128), стресс (256 на 1 RPM), бак (4000 мБ), расход (1 мБ/тик).

## Версия 2 (текстуры)
Замените файл `src/main/resources/assets/heavydiesel/textures/block/diesel_engine.png`. Сейчас там временная текстура из вашего .bbmodel.
Если поменяется UV модели, пришлите новый JSON, и я заново разрежу его на две половины.

## Что не проверено
Код сверялся с исходниками Create 1.21.1, но здесь нельзя скачать зависимости, поэтому сборка и запуск не тестировались.
