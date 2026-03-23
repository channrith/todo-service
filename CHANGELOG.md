# Changelog

All notable changes to **todo-service** are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html) for released versions.

## [Unreleased]

### Added

### Changed

### Deprecated

### Removed

### Fixed

### Security

## [1.0.0] - 2026-03-23

Initial Spring Boot release of todo-service.

### Security

- JWT validation with HMAC-256 shared secret (aligned with user-service).

### Added

- PostgreSQL persistence with Spring Data JPA and Hibernate.

---

**Workflow:** accumulate changes under **Unreleased** during development. When you tag a release, rename that section to `[version] - YYYY-MM-DD`, add a new empty **Unreleased** block above it, and optionally add compare URLs at the bottom of the file (see [Keep a Changelog](https://keepachangelog.com/en/1.1.0/)).
