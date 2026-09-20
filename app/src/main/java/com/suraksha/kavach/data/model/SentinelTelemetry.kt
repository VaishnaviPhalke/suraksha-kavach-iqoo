package com.suraksha.kavach.data.model

/**
 * SentinelTelemetry encapsulates real-time Snapdragon NPU metrics,
 * on-device localized LLM reasoning, voice override prompt state,
 * and zero-network air-gapped firewall counters.
 */
data class SentinelTelemetry(
    val riskScore: Float = 0.02f, // 0.02 - Low Risk as required
    val riskLevel: String = "Low (Safe)",
    val npuModelName: String = "Snapdragon NPU • Int8 Local Reasoning LLM",
    val npuInferenceLatencyMs: Float = 0.02f, // 0.02ms latency
    val npuCoreUtilization: Int = 14, // 14% friendly load
    val outboundNetworkRequests: Int = 0, // STRICTLY ZERO (Air-Gapped verification)
    val airGappedStatusText: String = "Air-Gapped | 0 Outbound Requests",
    val isVoicePromptActive: Boolean = true,
    val voicePromptText: String = "Unusual access detected. Confirm or Revoke?",
    val voiceListeningState: VoiceState = VoiceState.AWAITING_INPUT,
    val anomalyEvents: List<NpuAnomalyEvent> = emptyList()
)

enum class VoiceState {
    IDLE,
    PROMPTING,
    AWAITING_INPUT,
    CONFIRMED,
    REVOKED
}

data class NpuAnomalyEvent(
    val id: String,
    val timestamp: String,
    val checkName: String,
    val result: String,
    val isBenign: Boolean = true,
    val explanation: String
)
