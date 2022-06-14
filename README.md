### Need fix
change type of entity's id variables to primitives (nullable)

### Hibernate

- If you let hibernate generate DDL, it will automatically create not null constraints for primitive types.
- Uni-direction for @OneToMany relationship is a bad idea for performance due extra database operations with an extra table
