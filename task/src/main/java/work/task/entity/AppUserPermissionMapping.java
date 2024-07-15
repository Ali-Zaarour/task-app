package work.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user_permission_mapping")
public class AppUserPermissionMapping {

    @Id
    @ManyToOne
    @JoinColumn(name = "au_id", nullable = false, foreignKey = @ForeignKey(name = "app_user_permission_mapping_au_id_fkey"))
    private AppUser appUser;

    @Id
    @ManyToOne
    @JoinColumn(name = "aup_id", nullable = false, foreignKey = @ForeignKey(name = "app_user_permission_mapping_aup_id_fkey"))
    private AppUserPermission appUserPermission;

    @UpdateTimestamp
    @Column(name = "updated_at")
    protected Timestamp updatedAt;

    @Column(name = "deleted_at")
    protected Timestamp deletedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    protected Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false, foreignKey = @ForeignKey(name = "app_user_permission_mapping_created_by_fkey"))
    private AppUser createdBy;

}
