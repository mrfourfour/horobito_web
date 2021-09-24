package myproject.demo.Preference.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceRepository extends JpaRepository<Preference, PreferenceId> {
}
