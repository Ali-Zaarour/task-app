package work.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import work.task.utils.Constants;

import java.sql.Timestamp;
import java.util.*;

@Data
@Entity
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user")
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "username", nullable = false, unique = true, length = 200)
    private String username;

    @Column(name = "password", length = 115)
    private String password;

    @Column(name = "psw_last_update", nullable = false)
    private Timestamp pswLastUpdate;

    @ManyToOne
    @JoinColumn(name = "os_id", nullable = false, foreignKey = @ForeignKey(name = "app_user_os_id_fkey"))
    private OrganizationSide organizationSide;

    @ManyToOne
    @JoinColumn(name = "aus_id", nullable = false, foreignKey = @ForeignKey(name = "app_user_aus_id_fkey"))
    private AppUserStatus appUserStatus;

    @ManyToOne
    @JoinColumn(name = "aur_id", nullable = false, foreignKey = @ForeignKey(name = "app_user_aur_id_fkey"))
    private AppUserRole appUserRole;

    @Column(name = "login_attempts", columnDefinition = "int default 0")
    private int loginAttempts;

    @Column(name = "last_login_at")
    private Timestamp lastLoginAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    protected Timestamp updatedAt;

    @Column(name = "deleted_at")
    protected Timestamp deletedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    protected Timestamp createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", foreignKey = @ForeignKey(name = "app_user_self_fkey"))
    private AppUser createdBy;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AppUserPermissionMapping> permissionMappings;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Add user's role as a SimpleGrantedAuthority
        authorities.add(new SimpleGrantedAuthority(Constants.GRANTED_AUTHORITY_FIRST_KEY + appUserRole.getKey().toUpperCase()));

        // Add user's permissions
        authorities.addAll(permissionMappings.stream()
                .map(permissions -> new SimpleGrantedAuthority(
                        Constants.GRANTED_AUTHORITY_SECOND_KEY +
                                permissions.getAppUserPermission().getKey().toLowerCase()))
                .toList());

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return appUserStatus.getKey().equals("ACTIVATED");
    }
}
