package com.example.attendance.domain.usecase

import com.example.attendance.domain.repository.GroupRepository
import javax.inject.Inject

class GetGroupName @Inject constructor(
    private val groupName: GroupRepository
) {
   suspend operator fun invoke(groupId: String): String? = groupName.getName(groupId)
}