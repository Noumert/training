package projectServlet.model.entity;

import java.util.Objects;


/**
 * Created by Noumert on 12.08.2021.
 */
public class User{
    private Long user_id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean accountNonLocked;
    private RoleType role;

    public User(Long id, String firstName, String lastName, String email, String password, boolean accountNonLocked, RoleType role) {
        this.user_id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.accountNonLocked = accountNonLocked;
        this.role = role;
    }

    public User() {
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;

        return Objects.equals(user_id, user.user_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id);
    }

    public Long getId() {
        return this.user_id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    public RoleType getRole() {
        return this.role;
    }

    public void setId(Long user_id) {
        this.user_id = user_id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public String toString() {
        return "User(id=" + this.getId() + ", firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ", email=" + this.getEmail() + ", password=" + this.getPassword() + ", accountNonLocked=" + this.isAccountNonLocked() + ", role=" + this.getRole() + ")";
    }

    public static class UserBuilder {
        private Long user_id;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private boolean accountNonLocked;
        private RoleType role;

        UserBuilder() {
        }

        public UserBuilder id(Long id) {
            this.user_id = id;
            return this;
        }

        public UserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder accountNonLocked(boolean accountNonLocked) {
            this.accountNonLocked = accountNonLocked;
            return this;
        }

        public UserBuilder role(RoleType role) {
            this.role = role;
            return this;
        }

        public User build() {
            return new User(user_id, firstName, lastName, email, password, accountNonLocked, role);
        }

        public String toString() {
            return "User.UserBuilder(id=" + this.user_id + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ", email=" + this.email + ", password=" + this.password + ", accountNonLocked=" + this.accountNonLocked + ", role=" + this.role + ")";
        }
    }
}