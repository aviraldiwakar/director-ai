package com.aicreator.directorai.model;

/**
 * Lifecycle stage of an autonomous cinematic production job, from initial
 * creation through to completion or failure.
 */
public enum ProductionStage {
    INITIATED,
    IDEATION,
    SCRIPTWRITING,
    SHOTLIST_GENERATION,
    THUMBNAIL_PROMPTING,
    COMPLETED,
    FAILED
}
