package com.aicreator.directorai.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "feed_posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedPost {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID agentId;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Lob
    @Column(nullable = false)
    private String text;

    @Lob
    @Column(nullable = false)
    private String rationale;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "post_sources", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "source_url")
    private List<String> sources;

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = Instant.now();
        }
    }
}