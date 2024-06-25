package com.example.YESNightMarket.tool.googleSignIn

//@Composable
//fun SignInButton(
//    text: String,
//    loadingText: String = "Signing in...",
//    icon: Painter,
//    isLoading: Boolean = false,
//    shape: CornerBasedShape = MaterialTheme.shapes.medium,
//    borderColor: Color = Color.LightGray,
//    backgroundColor: Color = MaterialTheme.colorScheme.surface,
//    progressIndicatorColor: Color = MaterialTheme.colorScheme.primary,
//    onClick: () -> Unit
//) {
//    Surface(
//        modifier = Modifier.clickable(
//            enabled = !isLoading,
//            onClick = onClick
//        ),
//        shape = shape,
//        border = BorderStroke(width = 1.dp, color = borderColor),
//        color = backgroundColor
//    ) {
//        Row(
//            modifier = Modifier
//                .padding(
//                    start = 12.dp,
//                    end = 16.dp,
//                    top = 12.dp,
//                    bottom = 12.dp
//                ),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center,
//        ) {
//            Icon(
//                painter = icon,
//                contentDescription = "SignInButton",
//                tint = Color.Unspecified
//            )
//            Spacer(modifier = Modifier.width(8.dp))
//
//            Text(text = if (isLoading) loadingText else text)
//            if (isLoading) {
//                Spacer(modifier = Modifier.width(16.dp))
//                CircularProgressIndicator(
//                    modifier = Modifier
//                        .height(16.dp)
//                        .width(16.dp),
//                    strokeWidth = 2.dp,
//                    color = progressIndicatorColor
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun AuthView(
//    errorText: String?,
//    onClick: () -> Unit
//) {
//    var isLoading by remember { mutableStateOf(false) }
//
//    Surface{
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            SignInButton(
//                text = "Sign in with Google",
//                loadingText = "Signing in...",
//                isLoading = isLoading,
//                icon = painterResource(id = R.drawable.ic_google_logo),
//                onClick = {
//                    isLoading = true
//                    onClick()
//                }
//            )
//
//            errorText?.let {
//                isLoading = false
//                Spacer(modifier = Modifier.height(30.dp))
//                Text(text = it)
//            }
//        }
//    }
//}
//@ExperimentalAnimationApi
//@ExperimentalFoundationApi
//@ExperimentalCoroutinesApi
//@Composable
//fun AuthScreen(
//    authViewModel: AuthViewModel
//) {
//    val coroutineScope = rememberCoroutineScope()
//    var text by remember { mutableStateOf<String?>(null) }
//    val user by remember(authViewModel) { authViewModel.user }.collectAsState()
//    val signInRequestCode = 1
//
//    val authResultLauncher =
//        rememberLauncherForActivityResult(contract = AuthResultContract()) { task ->
//            try {
//                val account = task?.getResult(ApiException::class.java)
//                if (account == null) {
//                    text = "Google sign in failed"
//                } else {
//                    coroutineScope.launch {
//                        account.email?.let {
//                            account.displayName?.let { it1 ->
//                                authViewModel.signIn(
//                                    email = it,
//                                    displayName = it1,
//                                )
//                            }
//                        }
//                    }
//                }
//            } catch (e: ApiException) {
//                text = "Google sign in failed"
//            }
//        }
//
//    AuthView(
//        errorText = text,
//        onClick = {
//            text = null
//            authResultLauncher.launch(signInRequestCode)
//        }
//    )
//
//    user?.let {
//        HomeScreen(user = it)
//    }
//}
//@Composable
//fun HomeScreen(user: User) {
//    Scaffold { innerPadding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = "Hello, ${user.displayName}",
//                fontWeight = FontWeight.Bold,
//                style = MaterialTheme.typography.titleSmall,
//                fontSize = 30.sp
//            )
//            Spacer(modifier = Modifier.height(10.dp))
//            Text(text = user.email)
//        }
//    }
//}

