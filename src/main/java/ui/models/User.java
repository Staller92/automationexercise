package ui.models;

public class User {
    private final String name;
    private final String email;
    private final String password;

    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public User(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
    }

    public static class Builder {
        private String name;
        private String email;
        private String password;

        public Builder setName(String name) {
             this.name = name;
            return this;
        }
        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }
        public User build() {
            return new User(this);
        }

    }
    
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
