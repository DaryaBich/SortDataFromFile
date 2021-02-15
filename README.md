# Информация по проекту
Для написания проекта использовалась java версии 1.8
## Все используемые файлы и папки (в том числе промежуточные) создаются в директории resources:
  1. input - используется для записи сгенерированного входного файла
  2. output - используется для вывода результата
  3. tmp - директория для записи промежуточных данных
  
## Описание классов:
  Main - содержит вызовы функций связанных с подготовкой данных и файлов
  DataSorter - класс содержащий вызовы методов обработки данных
  FileWorker - занимается созданием и удалением файлов и директорий
  DataWorker - работает непосредственно с данными (сортирует, объединяет)
  DataWriter - выводит данные в файл
  DataReader - считывает данные из файла
  
## Задание параметров
  1. Чтобы уменьшить количество генерируемых строк необходимо
    в классе DataWrite в строке 10 изменить параметр genLineCount (для тестирования
    использовала значение 5000)
   2. Чтобы уменьшить размер блока на которые разбивается файл необходимо
   в классе DataWorker в строке 6 изменить параметр MAX_AVAILABLE_BYTES (для тестирования
   использовала значение 125)
