package com.sohyun.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.ResistanceConfig
import androidx.wear.compose.material.SwipeableDefaults
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import kotlin.math.roundToInt

/**
 *
 * */
@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun SoSwipeableView(
    modifier: Modifier = Modifier,
    swipeDirection: SwipeDirection = SwipeDirection.RIGHT,
    leftDismissDistance: Dp = 100.dp,
    rightDismissDistance: Dp = 100.dp,
    fractionalThreshold: Float = 0.5f,
    enable: Boolean = true,
    enableResistance: Boolean = true,
    onClickedContent: () -> Unit = {},
    content: @Composable RowScope.() -> Unit,
    background: @Composable BoxScope.() -> Unit,
) = BoxWithConstraints(modifier) {
    val density = LocalDensity.current
    val swipeableState = rememberSwipeableState(initialValue = DismissDirection.Default)
    val parentMaxWidth = constraints.maxWidth.toFloat()

    val thresholds = { from: DismissDirection, to: DismissDirection ->
        FractionalThreshold(fractionalThreshold)
    }

    val anchors = when (swipeDirection) {
        SwipeDirection.RIGHT -> mapOf(
            0f to DismissDirection.Default,
            (-with(density) { rightDismissDistance.toPx() } to DismissDirection.DismissLeft)
        )
        SwipeDirection.LEFT -> mapOf(
            0f to DismissDirection.Default,
            (with(density) { leftDismissDistance.toPx() } to DismissDirection.DismissRight)
        )
        else -> {
            mapOf(
                0f to DismissDirection.Default,
                (-with(density) { rightDismissDistance.toPx() } to DismissDirection.DismissLeft),
                (with(density) { leftDismissDistance.toPx() } to DismissDirection.DismissRight)
            )
        }
    }

    Box(
        modifier.swipeable(
            state = swipeableState,
            anchors = anchors,
            thresholds = thresholds,
            orientation = Orientation.Horizontal,
            enabled = enable,
            resistance = ResistanceConfig(
                basis = parentMaxWidth,
                factorAtMin = if (enableResistance) SwipeableDefaults.StandardResistanceFactor else 0f,
                factorAtMax = if (enableResistance) SwipeableDefaults.StandardResistanceFactor else 0f,
            )
        )
    ) {
        background()
        Row(
            content = content,
            modifier = Modifier.offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }.clickable { onClickedContent() }
        )
    }
}