package com.example.attendance.domain.usecase

import com.example.attendance.domain.model.util.PinResult
import com.example.attendance.domain.repository.AdminSecurityRepository
import com.example.attendance.domain.service.PinHasher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import java.time.Clock

class VerifyAdminPin @Inject constructor(
    private val security: AdminSecurityRepository,
    private val hasher: PinHasher,
    private val clock: Clock
) {
    suspend operator fun invoke(ordId: String, pin: String): PinResult{
        val now = clock.instant()
        val  state = security.getPinState(ordId)

        state.lockedUntil?.takeIf { it.isAfter(now) }?.let { return PinResult.Locked(it) }

        val ok = withContext(Dispatchers.Default){hasher.verify(pin, state.hash)}
        if (ok){
            security.resetFailures(ordId)
            return PinResult.Success
        }
        val failures = state.failedAttempt + 1
        return if(failures >= MAX_ATTEMPT){
            val until = now.plusSeconds(LOCK_SECONDS)
            security.recordFailure(ordId, 0, until)
            PinResult.Locked(until)
        } else {
            security.recordFailure(ordId, failures, null )
            PinResult.Wrong(MAX_ATTEMPT - failures)
        }
    }

    private companion object{
        const val MAX_ATTEMPT = 5
        const val LOCK_SECONDS = 60L
    }
}