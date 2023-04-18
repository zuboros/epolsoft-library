package com.example.epolsoftbackend.book;

import java.io.Serializable;

import com.example.epolsoftbackend.user.User;
import com.example.epolsoftbackend.topic.Topic;
import org.hibernate.HibernateException;
import org.hibernate.annotations.Type;
//import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.type.EnumType;

import javax.persistence.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;

class PostgreSQLEnumType extends EnumType {

    @Override
    public void nullSafeSet(PreparedStatement ps, Object obj, int index,
                            SharedSessionContractImplementor session) throws HibernateException, SQLException {
        if (obj == null) {
            ps.setNull(index, Types.OTHER);
        } else {
            ps.setObject(index, obj.toString(), Types.OTHER);
        }
    }
}

enum BookStatus {
    CREATED, WAIT_APPROVING, ACTIVED, BLOCKED, ARCHIVED
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "book")
public class Book implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    @Length(min = 2, max = 255)
    private String name;

    @Column(name = "short_description")
    @Length(min = 5, max = 150)
    private String shortDescription;

    @Column(name = "description")
    @Length(min = 20, max = 255)
    private String description;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "file_name")
    @Length(min = 0, max = 255)
    private String fileName;

    @Column(name = "file_path")
    @Length(min = 0, max = 255)
    private String filePath;

    @ManyToOne
    @JoinColumn(
            name = "topic_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Topic topicId;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            nullable = false
    )
    private User userId;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
