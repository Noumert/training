package projectServlet.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Noumert on 13.08.2021.
 */
public class UserDTO {
    private Long id;
    private boolean accountNonLocked;
    @NotNull
    @NotEmpty
    private String firstName;
    @NotNull
    @NotEmpty
    private String lastName;
    @Email
    @NotNull
    @NotEmpty
    private String email;
    @Size(min = 8, max = 16)
    @NotNull
    @NotEmpty
    private String password;

    public UserDTO(Long id, boolean accountNonLocked, @NotNull @NotEmpty String firstName, @NotNull @NotEmpty String lastName, @Email @NotNull @NotEmpty String email, @Size(min = 8, max = 16) @NotNull @NotEmpty String password) {
        this.id = id;
        this.accountNonLocked = accountNonLocked;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public UserDTO() {
    }

    public static UserDTOBuilder builder() {
        return new UserDTOBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    public @NotNull @NotEmpty String getFirstName() {
        return this.firstName;
    }

    public @NotNull @NotEmpty String getLastName() {
        return this.lastName;
    }

    public @Email @NotNull @NotEmpty String getEmail() {
        return this.email;
    }

    public @Size(min = 8, max = 16) @NotNull @NotEmpty String getPassword() {
        return this.password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setFirstName(@NotNull @NotEmpty String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(@NotNull @NotEmpty String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(@Email @NotNull @NotEmpty String email) {
        this.email = email;
    }

    public void setPassword(@Size(min = 8, max = 16) @NotNull @NotEmpty String password) {
        this.password = password;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserDTO)) return false;
        final UserDTO other = (UserDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        if (this.isAccountNonLocked() != other.isAccountNonLocked()) return false;
        final Object this$firstName = this.getFirstName();
        final Object other$firstName = other.getFirstName();
        if (this$firstName == null ? other$firstName != null : !this$firstName.equals(other$firstName)) return false;
        final Object this$lastName = this.getLastName();
        final Object other$lastName = other.getLastName();
        if (this$lastName == null ? other$lastName != null : !this$lastName.equals(other$lastName)) return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final Object this$password = this.getPassword();
        final Object other$password = other.getPassword();
        if (this$password == null ? other$password != null : !this$password.equals(other$password)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        result = result * PRIME + (this.isAccountNonLocked() ? 79 : 97);
        final Object $firstName = this.getFirstName();
        result = result * PRIME + ($firstName == null ? 43 : $firstName.hashCode());
        final Object $lastName = this.getLastName();
        result = result * PRIME + ($lastName == null ? 43 : $lastName.hashCode());
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final Object $password = this.getPassword();
        result = result * PRIME + ($password == null ? 43 : $password.hashCode());
        return result;
    }

    public String toString() {
        return "UserDTO(id=" + this.getId() + ", accountNonLocked=" + this.isAccountNonLocked() + ", firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ", email=" + this.getEmail() + ", password=" + this.getPassword() + ")";
    }

    public static class UserDTOBuilder {
        private Long id;
        private boolean accountNonLocked;
        private @NotNull @NotEmpty String firstName;
        private @NotNull @NotEmpty String lastName;
        private @Email @NotNull @NotEmpty String email;
        private @Size(min = 8, max = 16) @NotNull @NotEmpty String password;

        UserDTOBuilder() {
        }

        public UserDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserDTOBuilder accountNonLocked(boolean accountNonLocked) {
            this.accountNonLocked = accountNonLocked;
            return this;
        }

        public UserDTOBuilder firstName(@NotNull @NotEmpty String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserDTOBuilder lastName(@NotNull @NotEmpty String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserDTOBuilder email(@Email @NotNull @NotEmpty String email) {
            this.email = email;
            return this;
        }

        public UserDTOBuilder password(@Size(min = 8, max = 16) @NotNull @NotEmpty String password) {
            this.password = password;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(id, accountNonLocked, firstName, lastName, email, password);
        }

        public String toString() {
            return "UserDTO.UserDTOBuilder(id=" + this.id + ", accountNonLocked=" + this.accountNonLocked + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ", email=" + this.email + ", password=" + this.password + ")";
        }
    }
}

