package news;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/**
 * Главный класс, в котором начинает свою работу приложение
 */
public class NewsApplication {

    /**
     * Главный метод - начало работы приложения. Отсюда начинает свое исполнение приложение
     * @param args Аргументы, передаваемые при запуске
     */
    public static void main(String[] args) {
        SpringApplication.run(NewsApplication.class, args);
    }

}
