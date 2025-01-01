package com.example.gwweighscale.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.example.gwweighscale.rooms.entities.Credential
import com.example.gwweighscale.viewmodels.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun ShowCredentialsScreen(
    viewModel: LoginViewModel,
    onBack: () -> Unit
) {
    val credentials by viewModel.credentials.collectAsState(initial = emptyList())
    var isDialogVisible by remember { mutableStateOf(false) }
    var selectedCredential by remember { mutableStateOf<Credential?>(null) }
    var isDeleteConfirmationVisible by remember { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("Saved Credentials", fontWeight = FontWeight.Bold) },
                backgroundColor = Color(0xFF026163),
                contentColor = Color.White,
                elevation = 4.dp,
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (credentials.isEmpty()) {
                    Text(
                        text = "No credentials found.",
                        fontSize = 18.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                } else {
                    Text(
                        text = "Credentials List",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF026163),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(credentials) { credential ->
                            CredentialCard(
                                credential = credential,
                                onClick = {
                                    selectedCredential = credential
                                    isDialogVisible = true
                                },
                                onDelete = {
                                    selectedCredential = credential
                                    isDeleteConfirmationVisible = true
                                }
                            )
                        }
                    }
                }
            }
        }
    )



    // Dialog for Delete Confirmation
    if (isDeleteConfirmationVisible && selectedCredential != null) {
        ConfirmDeleteDialog(
            credential = selectedCredential!!,
            onConfirm = {
                viewModel.viewModelScope.launch {
                    viewModel.deleteCredential(selectedCredential!!)
                    selectedCredential = null
                    isDeleteConfirmationVisible = false
                    scaffoldState.snackbarHostState.showSnackbar("Credential deleted successfully")
                }
            },
            onDismiss = { isDeleteConfirmationVisible = false }
        )
    }
}

// 🪧 Credential Card Component
@Composable
fun CredentialCard(credential: Credential, onClick: () -> Unit, onDelete: () -> Unit) {
    Card(
        elevation = 4.dp,
        backgroundColor = Color(0xFFE8F5E9),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Machine Code: ${credential.machineCode}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "Password: ${credential.password}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red
                )
            }
        }
    }
}

@Composable
fun ConfirmDeleteDialog(credential: Credential, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Delete Credential", fontWeight = FontWeight.Bold) },
        text = {
            Text("Are you sure you want to delete the credential for Machine Code: ${credential.machineCode}?")
        },
        confirmButton = {
            Button(onClick = onConfirm, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
