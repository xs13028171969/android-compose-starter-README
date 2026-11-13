package com.example.androidcomposestarter.ui.island

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidcomposestarter.domain.AlertType
import com.example.androidcomposestarter.domain.IslandAlert
import com.example.androidcomposestarter.ui.theme.CapsuleBg
import com.example.androidcomposestarter.ui.theme.CapsuleBorder
import com.example.androidcomposestarter.ui.theme.ColdCyan
import com.example.androidcomposestarter.ui.theme.LeafGreen
import com.example.androidcomposestarter.ui.theme.Muted
import com.example.androidcomposestarter.ui.theme.OnDesktop
import com.example.androidcomposestarter.ui.theme.RainBlue
import com.example.androidcomposestarter.ui.theme.RestrictAmber

private val CapsuleShape = RoundedCornerShape(28.dp)

@Composable
fun YecaiIsland(
    primaryAlert: IslandAlert?,
    alerts: List<IslandAlert>,
    idleStatus: String,
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val accent = primaryAlert?.type.accentColor()
    Column(
        modifier = modifier
            .widthIn(min = 168.dp, max = 360.dp)
            .clip(CapsuleShape)
            .background(CapsuleBg)
            .border(1.dp, CapsuleBorder, CapsuleShape)
            .clickable(role = Role.Button, onClick = onClick)
            .animateContentSize()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AnimatedContent(
            targetState = Triple(primaryAlert, alerts.size, expanded),
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "island-content",
        ) { (alert, count, isExpanded) ->
            if (alert == null) {
                IslandLine(dot = LeafGreen, text = idleStatus)
            } else if (!isExpanded) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    IslandLine(
                        dot = accent,
                        text = alert.message,
                        modifier = Modifier.weight(1f, fill = false),
                    )
                    if (count > 1) {
                        Text(
                            text = "+${count - 1}",
                            color = Muted,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }
            } else {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "野菜岛",
                        color = LeafGreen,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(Modifier.height(8.dp))
                    alerts.forEach { item ->
                        Text(
                            text = item.title,
                            color = item.type.accentColor(),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                        )
                        Text(
                            text = item.message,
                            color = OnDesktop,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 8.dp),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun IslandLine(
    dot: Color,
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Spacer(
            Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(dot),
        )
        Text(
            text = text,
            color = OnDesktop,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

private fun AlertType?.accentColor(): Color = when (this) {
    AlertType.DRIVING_RESTRICTION -> RestrictAmber
    AlertType.RAIN -> RainBlue
    AlertType.TEMP_DROP -> ColdCyan
    null -> LeafGreen
}
