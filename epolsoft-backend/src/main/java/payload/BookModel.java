package payload;


import com.example.epolsoftbackend.entities.Topic;
import com.example.epolsoftbackend.entities.User;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
   public class BookModel {
   private String name;
   private Topic topic_id;
   private User user_id;
   private String description;
   private String shortDescription;
   private String file;
}
