package com.example.yesnightmarket.page

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import changeGmailAddress
import coil.compose.rememberAsyncImagePainter
import com.example.yesnightmarket.Data.Member
import com.example.yesnightmarket.Data.NightMarket
import com.example.yesnightmarket.Data.Store
import com.example.yesnightmarket.Data.identity
import com.example.yesnightmarket.R
import com.example.yesnightmarket.Screens
import com.example.yesnightmarket.tool.NightMarketInfo
import com.example.yesnightmarket.tool.backHandler.BackHandlerPress
import com.example.yesnightmarket.tool.create.CreateNightMarket
import com.example.yesnightmarket.tool.create.CreateStoreMessage
import com.example.yesnightmarket.tool.create.CreateStores
import com.example.yesnightmarket.tool.custmozed.UdmtextFields
import com.example.yesnightmarket.tool.custmozed.log
import com.example.yesnightmarket.tool.custmozed.loge
import com.example.yesnightmarket.tool.hint.showToast
import com.example.yesnightmarket.tool.http.Retrofit
import com.example.yesnightmarket.tool.http.URL
import com.example.yesnightmarket.tool.pictureTool.uriToFile
import com.example.yesnightmarket.tool.savedataclass.readDataClass
import com.example.yesnightmarket.tool.savedataclass.saveDataClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody


@Composable
fun SettingPage(modifier: Modifier, navController: NavHostController) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val member = readDataClass(context, "Member") ?: Member()
    //改名

    Scaffold(
        topBar = {
            Row(
                Modifier
                    .padding(top = statusBarHeight)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "設定", fontSize = 30.sp)
            }
        }
    ) {

        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(it), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                //使用者顯示設定
                Spacer(modifier = Modifier.padding(10.dp))
                UserRow(member, navController, context)
                //log(getPermission.toString()+" "+member.identity+" "+identity.HighestAdministrator.name)
                if ((member.identity == identity.HighestAdministrator.name || member.identity == identity.Administrator.name)) {
                    SetPermission(member, navController)
                }
                if (member.identity == identity.Administrator.name) {
                    if (member.memberProperty == "") {
                        NewNightMarket(modifier, member, navController)
                    } else {
                        undateNightMarket(modifier, member, navController)
                    }


                }
                if(member.identity == identity.Shopkeeper.name){
                    NewStore(member,context,navController)

                }
                SignOut(context, navController)
            }

        }

    }

    BackHandlerPress() {
        Log.i("9453", "返回")
        navController.navigate(Screens.MainPage.name)
    }

}


@Composable
fun UserRow(member: Member, navController: NavHostController, context: Context) {
    var changeNameisClicked by remember {
        mutableStateOf(false)
    }
    Row {
        Text(text = stringResource(R.string.user) + "：")
        if (member.memberGmail == "") {
            //如果沒註冊，則註冊
            saveDataClass(context, "SourceNav", Screens.SettingPage.name)
            Text(
                text = stringResource(R.string.not_logged_in),
                color = Color.Blue,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable { navController.navigate(Screens.LoginPage.name) })
        } else if (member.memberName == "" && !changeNameisClicked) {
            //如果尚未命名
            Text(
                text = stringResource(R.string.click_to_give_name),
                color = Color.Gray,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable { changeNameisClicked = true })

        } else if (changeNameisClicked) {
            //如果已命名
            member.memberName = UdmtextFields(
                keyboardType = KeyboardType.Text, modifier = Modifier
                    .padding(end = 10.dp)
                    .height(60.dp)
            ) { name ->
                member.memberName = name
                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        Retrofit.apiService.updateMemberByGmail(member.memberGmail, member)
                        saveDataClass(context, "Member", member)
                        changeNameisClicked = false
                    } catch (e: Exception) {
                        loge(e.toString())
                        e.printStackTrace()
                    }

                }
            }
            //返回設定
            BackHandlerPress() {
                Log.i("9453", "返回")
                navController.navigate(Screens.SettingPage.name)
            }
        } else {
            Text(
                text = member.memberName,
                modifier = Modifier.clickable { changeNameisClicked = true })
        }
        Icon(Icons.Default.Refresh, contentDescription = null, Modifier.clickable {
            CoroutineScope(Dispatchers.Main).launch {
//                val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
//                val jsonAdapter: JsonAdapter<Member> = moshi.adapter(Member::class.java)
//                val jsonRequest = jsonAdapter.toJson(member)
//                log(jsonRequest)
                val refresh = Retrofit.apiService.getMemberByGmail(member.memberGmail)
                saveDataClass(context, "Member", refresh)
                log(member.toString())
            }

        })
    }
}

@Composable
fun SetPermission(member: Member, navController: NavHostController) {
    val context = LocalContext.current
    var getPermission by remember {
        mutableStateOf(false)
    }



    if (!getPermission) {
        OutlinedButton(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp), onClick = {
            getPermission = true
        }) {
            Text(text = stringResource(R.string.give_permission), fontSize = 20.sp)
        }
    } else {
        var modifyAccount = UdmtextFields(
            stringResource = R.string.modify_account,
            modifier = Modifier.padding(10.dp),
            imeAction = ImeAction.Next
        )
        var modifyIdentity by remember { mutableStateOf(identity.General.name) }
        Text(text = "目前選擇：" + modifyIdentity)
        if (member.identity == identity.HighestAdministrator.name) {
            identity.entries.forEach {
                OutlinedButton(modifier = Modifier
                    .padding(1.dp)
                    .fillMaxWidth()
                    .padding(3.dp), onClick = {
                    modifyIdentity = it.name
                }) {
                    Text(text = it.name, fontSize = 15.sp)
                }
            }
        } else {
            OutlinedButton(modifier = Modifier
                .padding(1.dp)
                .fillMaxWidth()
                .padding(3.dp), onClick = {
                modifyIdentity = identity.General.name
            }) {
                Text(text = identity.General.name, fontSize = 15.sp)
            }
            OutlinedButton(modifier = Modifier
                .padding(1.dp)
                .fillMaxWidth()
                .padding(3.dp), onClick = {
                modifyIdentity = identity.Shopkeeper.name
            }) {
                Text(text = identity.Shopkeeper.name, fontSize = 15.sp)
            }
        }

        OutlinedButton(onClick = {
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    modifyAccount = changeGmailAddress(modifyAccount).toString()
                    val modifyMember = Retrofit.apiService.getMemberByGmail(modifyAccount)
                    log(modifyMember.toString())

                    if ((member.identity == identity.Administrator.name) && (modifyMember.identity == identity.HighestAdministrator.name || modifyMember.identity == identity.Administrator.name)) { //阻擋對同等權限修改
                        showToast(context, R.string.failed)
                    } else {
                        if (modifyIdentity == identity.General.name) {
                            modifyMember.memberProperty = ""
                        }
                        modifyMember.identity = modifyIdentity
                        val response = Retrofit.apiService.updateMemberByGmail(
                            modifyMember.memberGmail,
                            modifyMember
                        )
                        if (response.code() == 200) {
                            showToast(context, R.string.successful)
                        } else {
                            showToast(context, R.string.failed)
                        }
                    }


                } catch (e: Exception) {
                    loge(e.toString())
                    showToast(context, R.string.failed)
                    e.printStackTrace()
                }
                getPermission = false
            }
        }) {
            Text(text = stringResource(id = R.string.ok), fontSize = 20.sp)
        }
        BackHandlerPress() {
            Log.i("9453", "返回")
            navController.navigate(Screens.SettingPage.name)
        }
    }
}

@Composable
fun NewNightMarket(modifier: Modifier, member: Member, navController: NavHostController) {
    val context = LocalContext.current
    var create by remember {
        mutableStateOf(false)
    }
    var confirm by remember {
        mutableStateOf(false)
    }
    var nightMarket by remember {
        mutableStateOf(NightMarket())
    }
    var uploadResult by remember { mutableStateOf<String?>(null) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            selectedImageUri = uri
        }
    )
    OutlinedButton(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth()
        .padding(start = 10.dp, end = 10.dp),
        onClick = {
            create = !create
        }) {
        Text(
            text = stringResource(id = R.string.create) + stringResource(id = R.string.night_market),
            fontSize = 20.sp
        )
    }
    if (create) {
        nightMarket.name = UdmtextFields(
            stringResource = R.string.night_market,
            keyboardType = KeyboardType.Text,
            extraMsg = stringResource(id = R.string.name),
            imeAction = ImeAction.Next
        )
        nightMarket.address = UdmtextFields(
            stringResource = R.string.address,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )
        nightMarket.phone = UdmtextFields(
            stringResource = R.string.phone,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )
        nightMarket.openingHours = UdmtextFields(
            stringResource = R.string.opening_hours,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        )
        selectedImageUri?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp),
                contentScale = ContentScale.Crop
            )
        }
        OutlinedButton(onClick = { confirm = true }) {
            Text(text = stringResource(id = R.string.ok), fontSize = 20.sp)
        }
    }
    if (confirm) {
        CreateStores(nightMarket.name)
        member.memberProperty = nightMarket.name
        LaunchedEffect(Unit) {
            nightMarket.imageUrl = URL + "uploads/" + nightMarket.name + ".jpg"
            Retrofit.apiService.updateMemberByGmail(member.memberGmail, member)
            saveDataClass(context, "Member", member)
            val response = Retrofit.apiService.addNightMarket(nightMarket)
            if (response.code() == 200) {
                Log.i("linpoi", "成功")

            } else {
                Log.i("linpoi", "失敗$response")
            }
        }
        navController.navigate(Screens.SettingPage.name)
    }
}

suspend fun uploadImageWithDetails(
    context: Context,
    uri: Uri,
    description: String,
    userId: String,
    fileName: String,  // 接收文件名参数
) {
    try {
        val file = uriToFile(context, uri) ?: throw Exception("無法獲取文件")

        // 使用指定的文件名，而不是原始文件名
        val finalFileName = if (fileName.isNotEmpty()) fileName else file.name
        val encodedFileName = finalFileName.encodeToByteArray().toString(Charsets.UTF_8)

        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val multipartBody = MultipartBody.Part.createFormData("image", encodedFileName, requestBody)

        val descriptionBody = description.toRequestBody("text/plain".toMediaTypeOrNull())
        val userIdBody = userId.toRequestBody("text/plain".toMediaTypeOrNull())

        val response =
            Retrofit.apiService.uploadImageWithDetails(multipartBody, descriptionBody, userIdBody)
        if (response.success) {
            log("成功")
        } else {
            log("失敗：${response.message}")
        }
    } catch (e: Exception) {
        log("失敗：${e.message}")
    }
}

@Composable
fun undateNightMarket(modifier: Modifier, member: Member, navController: NavHostController) {
    val context = LocalContext.current
    var nightMarket by remember {
        mutableStateOf(NightMarket())
    }
    var edit by remember {
        mutableStateOf(false)
    }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            selectedImageUri = uri
        }
    )
    Column(modifier = modifier) {
        NightMarketInfo(nightMarket, navController)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(0.5.dp, Color.Gray), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(onClick = { edit = true }) {
                Text(text = stringResource(R.string.edit))
            }
            OutlinedButton(onClick = {
                member.memberProperty = ""
                saveDataClass(context, "Member", member)
                navController.navigate(Screens.SettingPage.name)
            }) {
                Text(text = stringResource(R.string.delete))
            }
        }
        if (edit) {
            nightMarket.name = UdmtextFields(
                stringResource = R.string.night_market,
                keyboardType = KeyboardType.Text,
                showContent = nightMarket.name,
                extraMsg = stringResource(id = R.string.name),
                imeAction = ImeAction.Next
            )
            nightMarket.address = UdmtextFields(
                stringResource = R.string.address,
                keyboardType = KeyboardType.Text,
                showContent = nightMarket.address,
                imeAction = ImeAction.Next
            )
            nightMarket.phone = UdmtextFields(
                stringResource = R.string.phone,
                keyboardType = KeyboardType.Text,
                showContent = nightMarket.phone,
                imeAction = ImeAction.Next
            )
            nightMarket.openingHours = UdmtextFields(
                stringResource = R.string.opening_hours,
                keyboardType = KeyboardType.Text,
                showContent = nightMarket.openingHours,
                imeAction = ImeAction.Done
            )
            OutlinedButton(onClick = { imagePickerLauncher.launch("image/*") }) {
                androidx.compose.material.Text("選擇圖片")
            }
            if (selectedImageUri == null) {
                Image(
                    painter = rememberAsyncImagePainter(nightMarket.imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(16.dp),
                    contentScale = ContentScale.Crop
                )
            }
            selectedImageUri?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(16.dp),
                    contentScale = ContentScale.Crop
                )
            }
            OutlinedButton(onClick = {
                edit = false
                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        selectedImageUri?.let { uri ->
                            uploadImageWithDetails(
                                context,
                                uri,
                                "description",
                                member.memberGmail,
                                nightMarket.name
                            )
                            nightMarket.imageUrl = URL + "uploads/" + nightMarket.name + ".jpg"
                        }
                        Retrofit.apiService.updateNightMarket(nightMarket.name, nightMarket)
                        nightMarket =
                            Retrofit.apiService.getNightMarketByName(member.memberProperty)
                    } catch (e: Exception) {
                        loge(e.toString())
                        e.printStackTrace()
                    }

                }
            }) {
                Text(text = stringResource(id = R.string.ok), fontSize = 20.sp)
            }
        }
    }

    LaunchedEffect(Unit) {
        try {
            nightMarket = Retrofit.apiService.getNightMarketByName(member.memberProperty)
        } catch (e: Exception) {
            loge(e.toString())
            e.printStackTrace()
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewStore(member: Member,context: Context, navController: NavHostController) {
    var confirm by remember {
        mutableStateOf(false)
    }

    var store by remember {
        mutableStateOf(Store())
    }
    var clike by remember {
        mutableStateOf(false)
    }
    var listNightMarket by remember {
        mutableStateOf(listOf<NightMarket>())
    }
    var select by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        try {
            listNightMarket = Retrofit.apiService.getNightMarkets()
            log(listNightMarket.toString())
        } catch (e: Exception) {
            loge(e.toString())
            e.printStackTrace()
        }
    }
    if(!clike){
        OutlinedButton(onClick = { clike=!clike }) {
            Text(text = stringResource(R.string.new_store))
        }
    }else{
        Column {
            Text(text = "選擇的夜市：${select}")
            Row {
                listNightMarket.forEach {
                    OutlinedButton(onClick = { select=it.name }) {
                        Text(text = it.name)
                    }
                }
            }
            store.name = UdmtextFields(
                stringResource = R.string.store_name,
                keyboardType = KeyboardType.Text,
                extraMsg = stringResource(id = R.string.name),
                imeAction = ImeAction.Next
            )
            store.address = UdmtextFields(
                stringResource = R.string.address,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
            store.phone = UdmtextFields(
                stringResource = R.string.phone,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
            store.openingHours = UdmtextFields(
                stringResource = R.string.opening_hours,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
            store.description = UdmtextFields(
                stringResource = R.string.description,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
            OutlinedButton(onClick = { confirm = true }) {
                Text(text = stringResource(id = R.string.ok), fontSize = 20.sp)
            }
        }
        if (confirm) {
            CreateStoreMessage(select,store.name, "name")
            member.memberProperty = store.name
            LaunchedEffect(Unit) {
                store.imageUrl = URL + "uploads/" + store.name + ".jpg"
                Retrofit.apiService.updateMemberByGmail(member.memberGmail, member)
                saveDataClass(context, "Member", member)
////                val response = Retrofit.apiService.addNightMarket(nightMarket)
//                if (response.code() == 200) {
//                    Log.i("linpoi", "成功")
//
//                } else {
//                    Log.i("linpoi", "失敗$response")
//                }
            }
            navController.navigate(Screens.SettingPage.name)
        }

    }
}

@Composable
fun SignOut(context: Context, navController: NavHostController) {
    Text(
        text = stringResource(R.string.signout),
        color = Color.Red,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier.clickable {
            saveDataClass(context, "Member", Member())
            navController.navigate(Screens.SettingPage.name)
            showToast(context, R.string.signout)
        })
}