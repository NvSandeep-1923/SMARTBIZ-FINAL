package com.example.smartbiz.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TestText() {
    Text(text = "Hello SmartBiz")
}

@Preview
@Composable
fun TestPreview() {
    TestText()
}
