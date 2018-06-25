/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
#include <string.h>
#include <jni.h>
//#include "platform/android/jni/JniHelper.h"
#include <android/log.h>
#include <string.h>

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/ioctl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <dlfcn.h> 
#include <jni.h>
#include <android/log.h>
/* This is a trivial JNI example where we use a native method
 * to return a new VM String. See the corresponding Java source
 * file located at:
 *
 *   apps/samples/hello-jni/project/src/com/example/hellojni/HelloJni.java
 */
jstring
Java_com_example_hellojni_HelloJni_stringFromJNI( JNIEnv* env,
                                                  jobject thiz )
{
#if defined(__arm__)
  #if defined(__ARM_ARCH_7A__)
    #if defined(__ARM_NEON__)
      #if defined(__ARM_PCS_VFP)
        #define ABI "armeabi-v7a/NEON (hard-float)"
      #else
        #define ABI "armeabi-v7a/NEON"
      #endif
    #else
      #if defined(__ARM_PCS_VFP)
        #define ABI "armeabi-v7a (hard-float)"
      #else
        #define ABI "armeabi-v7a"
      #endif
    #endif
  #else
   #define ABI "armeabi"
  #endif
#elif defined(__i386__)
   #define ABI "x86"
#elif defined(__x86_64__)
   #define ABI "x86_64"
#elif defined(__mips64)  /* mips64el-* toolchain defines __mips__ too */
   #define ABI "mips64"
#elif defined(__mips__)
   #define ABI "mips"
#elif defined(__aarch64__)
   #define ABI "arm64-v8a"
#else
   #define ABI "unknown"
#endif

    return (*env)->NewStringUTF(env, "Hello from JNI !  Compiled with ABI " ABI ".");
}

//extern jstring Java_com_aspire_demo_Demo_fsdfs(  JNIEnv* env, jobject obj , jstring s);
jstring Java_com_aspire_demo_MMPayUtil_dfffd(  JNIEnv* env, jobject obj , jstring s)
 {
	 jclass cls =(*env)->FindClass(env,"com/aspire/demo/Demo");
	 jmethodID mid = (*env)->GetMethodID(env,cls, "callbackString", "(Ljava/lang/String;)V");
	 (*env)->CallVoidMethod(env,obj, mid ,(*env)->NewStringUTF(env,"haha"));
   //Java_com_aspire_demo_Demo_fsdfs(env,obj,s);
   
 	return s;
 }

jstring Java_com_aspire_demo_MMPayUtil_getstrcontent(  JNIEnv* env, jobject obj , jstring s1, jstring s2
		, jstring s3, jstring s4, jstring s5, jstring s6, jstring s7, jstring s8
		, jstring s9,jstring s10)
 {
	//jclass cls =(*env)->FindClass(env,"com/aspire/demo/MMPayUtil");
	//jmethodID mid = (*env)->GetStaticMethodID(env,cls, "getOrderContent", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;");

	return s1;//(jstring)(*env)->CallStaticObjectMethod(env,cls,mid,s1,s2,s3,s4,s5,s6,s7,s8,s9,s10);
	
;
 }



jstring Java_org_cocos2dx_hellocpp_HelloCpp_getstr( JNIEnv* env, jobject obj , jstring s)
{
	jclass cls =(*env)->FindClass(env,"org/cocos2dx/hellocpp/HelloCpp");
	jmethodID mid =(*env)->GetMethodID(env,cls, "callbackString", "(Ljava/lang/String;)V");
	(*env)->CallVoidMethod(env,obj, mid ,(*env)->NewStringUTF(env,"haha"));

	return s;
}



jstring getvalue0(JNIEnv* env,char * name,char * method)
{
	jclass cls =(*env)->FindClass(env,name);//"com/iap/demo/weiying/MMPayUtil"
	jmethodID mid = (*env)->GetStaticMethodID(env,cls, method, "()Ljava/lang/String;");
	return (jstring)(*env)->CallStaticObjectMethod(env,cls,mid);

}

jstring getvalue_en(JNIEnv* env,char * name,char * method,jlong para)
{
	jclass cls =(*env)->FindClass(env,name);//"com/iap/demo/weiying/MMPayUtil"
	jmethodID mid = (*env)->GetStaticMethodID(env,cls, method, "(J)Ljava/lang/String;");
	return (jstring)(*env)->CallStaticObjectMethod(env,cls,mid,para);

}

jlong getlvalue(JNIEnv* env,char * name,char * method,jstring para)
{
   jclass cls =(*env)->FindClass(env,name);//"com/iap/demo/weiying/MMPayUtil"
   jmethodID mid = (*env)->GetStaticMethodID(env,cls, method, "(Ljava/lang/String;)J");

   return (jlong)(*env)->CallStaticLongMethod(env,cls,mid,para);;
}

int getIvalue(JNIEnv* env,char * name,char * method,jstring para)
{
   jclass cls =(*env)->FindClass(env,name);//"com/iap/demo/weiying/MMPayUtil"
   jmethodID mid = (*env)->GetStaticMethodID(env,cls, method, "(Ljava/lang/String;)I");
   (*env)->CallStaticObjectMethod(env,cls,mid,para);
   return (int)(*env)->CallStaticObjectMethod(env,cls,mid,para);
}

jstring getlvalue_d(JNIEnv* env,char * name,char * method,jstring para)
{
   jclass cls =(*env)->FindClass(env,name);//"com/iap/demo/weiying/MMPayUtil"
   jmethodID mid = (*env)->GetStaticMethodID(env,cls, method, "(Ljava/lang/String;)Ljava/lang/String;");
   return (jstring)(*env)->CallStaticObjectMethod(env,cls,mid,para);
}

char * util_g = "com/iap/demo/weiying/Util_G";
char * debugMD ="debug_e";

void debug_str(JNIEnv* env,char * para)
{
	 jstring strencode = (*env)->NewStringUTF(env,para);
   jclass cls =(*env)->FindClass(env,util_g);//"com/iap/demo/weiying/MMPayUtil"
   jmethodID mid = (*env)->GetStaticMethodID(env,cls, debugMD, "(Ljava/lang/String;Ljava/lang/String;)V");
   (*env)->CallStaticVoidMethod(env,cls,mid,strencode,strencode);
   return;
}

void debug_str2(JNIEnv* env,jstring para)
{
   jclass cls =(*env)->FindClass(env,util_g);//"com/iap/demo/weiying/MMPayUtil"
   jmethodID mid = (*env)->GetStaticMethodID(env,cls, debugMD, "(Ljava/lang/String;Ljava/lang/String;)V");
   (*env)->CallStaticVoidMethod(env,cls,mid,para,para);
   return;
}

char* jstringTostring(JNIEnv* env, jstring jstr)
{        
	char* rtn = NULL;
	jclass clsstring = (*env)->FindClass(env,"java/lang/String");
	jstring strencode = (*env)->NewStringUTF(env,"utf-8");
	jmethodID mid = (*env)->GetMethodID(env,clsstring, "getBytes", "(Ljava/lang/String;)[B");
	jbyteArray barr= (jbyteArray)(*env)->CallObjectMethod(env,jstr, mid, strencode);
	jsize alen = (*env)->GetArrayLength(env,barr);
	jbyte* ba = (*env)->GetByteArrayElements(env,barr, JNI_FALSE);
	if (alen > 0)
	{
		rtn = (char*)malloc(alen + 1);
		memcpy(rtn, ba, alen);
		rtn[alen] = 0;
	}
	(*env)->ReleaseByteArrayElements(env,barr, ba, 0);
	return rtn;
}

void * openLib( JNIEnv* env,jstring pPKName)
{
	  char * ppkname;
	  int bCOK = 0;
	  char libpath[150] = {0};
	    
	  ppkname = jstringTostring(env,pPKName);
	  sprintf(libpath,"/data/app-lib/%s-%d/libsmsiap.so",ppkname,1);
		
		//debug_str(env,libpath);
			
		void *pdlHandle = dlopen(libpath, RTLD_LAZY);  
		if( pdlHandle == NULL )
    {
    	   sprintf(libpath,"/data/app-lib/%s-%d/libsmsiap.so",ppkname,2);
    	   pdlHandle = dlopen(libpath, RTLD_LAZY);  
    }
    
    if( pdlHandle == NULL )
    {
    	   sprintf(libpath,"/data/app-lib/%s-%d/libsmsiap.so",ppkname,3);
    	   pdlHandle = dlopen(libpath, RTLD_LAZY);  
    }
    
    free(ppkname);
    return pdlHandle;
}
//com.iap.demo.weiying
jstring Java_com_iap_demo_weiying_MMPayUtil_getstrcontent(JNIEnv* env,
		jobject obj , jstring str1, jstring str2)
 {
	jstring s1;
	jstring s2;
	jstring s3;
	jstring s4;
	jstring s5;
	jstring s6;
	jstring s7;
	jstring s8;
	jstring s9;
	jstring s10;
	jstring retstr;
	
	jstring imei;
	jstring imsi;
  
	char  paracls[50] = {0};//"com/iap/demo/weiying/MMPayUtil";
	char  ztya[50] = {0};// = "com/iap/demo/weiying/ztya";
	char  mmcls[50] = {0};// = "com/iap/demo/weiying/MMPayUtil";
  char  getMSGC[256]={0};// = "Java_mm_sms_purchasesdk_fingerprint_IdentifyApp_SMSOrderContent";
  char  getOdreIDC[256]={0};// = "Java_mm_sms_purchasesdk_fingerprint_IdentifyApp_generateTransactionID";
  
  char encodeMD[10] ={0};//"en";
  char parseLongMD[30] = {0};//"parseLong";
  char genIDMD[30] = {0};//"dsfjtie";
  char  getMSMConMD[30] ={0};//"getstefte";
  char  getTimeMD[10] = {0};//"gt";
  char  getStrMD[20] = {0};//"getStr"
  char  getStr1MD[20] = {0};// ="getStr1";
  char  getStr4MD[20] = {0};// ="getStr4";
  char getStr5MD [20] = {0};//="getStr5";
  char  getStr6MD [20] = {0};//="getStr6";
  char  getStr7MD [20] = {0};//="getStr7";
  char  getStr8MD[20] = {0};// ="getStr8";   
  char  getStr9MD [20] = {0};//="getStr9";
  
  
 /* char * paracls = "com/iap/demo/weiying/MMPayUtil";
	char *  ztya =  "com/iap/demo/weiying/ztya";
	char  * mmcls = "com/iap/demo/weiying/MMPayUtil";
  char  getMSGC[256]={0};//"Java_mm_sms_purchasesdk_fingerprint_IdentifyApp_SMSOrderContent";//3
 // char * getMSGC = "Java_mm_sms_purchasesdk_fingerprint_IdentifyApp_SMSOrderContent";//2
 char  getOdreIDC[256]={0};// = "Java_mm_sms_purchasesdk_fingerprint_IdentifyApp_generateTransactionID";//1
  
  char  * encodeMD ="en";
  //char parseLongMD[30] = {0};//"parseLong";
   char * parseLongMD = "parseLong";//2
 char genIDMD[30] = {0};//char  * genIDMD = "dsfjtie";//2
  char *  getMSMConMD ="getstefte";
  char *  getTimeMD = "gt";
  char *  getStrMD = "getStr";
  char *  getStr1MD ="getStr1";
  char *  getStr4MD = "getStr4";
  char *  getStr5MD  ="getStr5";
  char *  getStr6MD= "getStr6";
  char *  getStr7MD = "getStr7";
  char *  getStr8MD = "getStr8";   
  char *  getStr9MD  = "getStr9";*/
     
  jstring pkname;

  void *pdlHandle ;
  int bCOK = 0;
  jclass cls ;
	jmethodID mid ;
	
	  int index = 0;
	  
	  
	   //"com/iap/demo/weiying/";
	  char clspath[100] = {0};
	  index = 0;
	  clspath[index ++] ='c';
	  clspath[index ++] ='o';
	  clspath[index ++] ='m';
	  clspath[index ++] ='/';
	  clspath[index ++] ='i';
	  clspath[index ++] ='a';
	  clspath[index ++] ='p';
	  clspath[index ++] ='/';
	  clspath[index ++] ='d';
	  clspath[index ++] ='e';
	  clspath[index ++] ='m';
	  clspath[index ++] ='o';
	  clspath[index ++] ='/';
	  clspath[index ++] ='w';
	  clspath[index ++] ='e';
	  clspath[index ++] ='i';
	  clspath[index ++] ='y';
	  clspath[index ++] ='i';
	  clspath[index ++] ='n';
	  clspath[index ++] ='g';
    clspath[index ++] ='/';
	   
	    //debug_str( env,"ok2");
	  //MMPayUtil";
	  char MMPayUtil[20] = {0};
	  index = 0;
	  MMPayUtil[index ++] ='M';
	  MMPayUtil[index ++] ='M';
	  MMPayUtil[index ++] ='P';
	  MMPayUtil[index ++] ='a';
	  MMPayUtil[index ++] ='y';
	  MMPayUtil[index ++] ='U';
	  MMPayUtil[index ++] ='t';
	  MMPayUtil[index ++] ='i';
	  MMPayUtil[index ++] ='l';
	  
	 debug_str( env,"ok3");
	   //"com/iap/demo/weiying/MMPayUtil";
	  sprintf(paracls,"%s%s",clspath,MMPayUtil);
	  
	  //"com/iap/demo/weiying/MMPayUtil";
	  sprintf(mmcls,"%s%s",clspath,MMPayUtil);
	  
	  //ztya";
	  char ztyana[20] = {0};
	  index = 0;
	  ztyana[index ++] ='z';
	  ztyana[index ++] ='t';
	  ztyana[index ++] ='y';
	  ztyana[index ++] ='a';

	  //"com/iap/demo/weiying/ztya";
	  sprintf(ztya,"%s%s",clspath,ztyana);
	   
	  //  debug_str( env,"ok4");
	    
	   //"getStr";
	  index = 0;
	  getStrMD[index ++] ='g';
	  getStrMD[index ++] ='e';
	  getStrMD[index ++] ='t';
	  getStrMD[index ++] ='S';
	  getStrMD[index ++] ='t';
	  getStrMD[index ++] ='r';
	  
	  sprintf(getStr1MD,"%s%d",getStrMD,1);
	  sprintf(getStr4MD,"%s%d",getStrMD,4);
	  sprintf(getStr5MD,"%s%d",getStrMD,5);
	  sprintf(getStr6MD,"%s%d",getStrMD,6);
	  sprintf(getStr7MD,"%s%d",getStrMD,7);
	  sprintf(getStr8MD,"%s%d",getStrMD,8);
	  sprintf(getStr9MD,"%s%d",getStrMD,9);  
	        
	  //"gt";
	  index = 0;
	  getTimeMD[index ++] ='g';
	  getTimeMD[index ++] ='t';
	  
	  //"getstefte";
	   index = 0;
	   getMSMConMD[index ++] ='g';
	   getMSMConMD[index ++] ='e';
	   getMSMConMD[index ++] ='t';
	   getMSMConMD[index ++] ='s';
	   getMSMConMD[index ++] ='t';
	   getMSMConMD[index ++] ='e';
	   getMSMConMD[index ++] ='f';
	   getMSMConMD[index ++] ='t';
	   getMSMConMD[index ++] ='e';
	  
	  //"en"
	  index = 0;
	  encodeMD[index ++] ='e';
	  encodeMD[index ++] ='n';
	  
	  
	  //"parseLong";
	  index = 0;
	  parseLongMD[index ++] ='p';
	  parseLongMD[index ++] ='a';
	  parseLongMD[index ++] ='r';
	  parseLongMD[index ++] ='s';
	  parseLongMD[index ++] ='e';
	  parseLongMD[index ++] ='L';
	  parseLongMD[index ++] ='o';
	  parseLongMD[index ++] ='n';
	  parseLongMD[index ++] ='g';
	  
	        //3
	         //debug_str( env,"ok5");
	  //"Java_mm_sms_purchasesdk_fingerprint_IdentifyApp_SMSOrderContent";
	  index = 0;
	  char SMSOrderContentstr[100]={0};
	   //char* SMSOrderContentstr="IdentifyApp_SMSOrderContent";
	  SMSOrderContentstr[index ++] = 'I';
		SMSOrderContentstr[index ++] = 'd';
		SMSOrderContentstr[index ++] = 'e';
		SMSOrderContentstr[index ++] = 'n';
		SMSOrderContentstr[index ++] = 't';
		SMSOrderContentstr[index ++] = 'i';
		SMSOrderContentstr[index ++] = 'f';
		SMSOrderContentstr[index ++] = 'y';
		SMSOrderContentstr[index ++] = 'A';
		SMSOrderContentstr[index ++] = 'p';
		SMSOrderContentstr[index ++] = 'p';
		SMSOrderContentstr[index ++] = '_';
		SMSOrderContentstr[index ++] = 'S';
		SMSOrderContentstr[index ++] = 'M';
		SMSOrderContentstr[index ++] = 'S';
		SMSOrderContentstr[index ++] = 'O';
		SMSOrderContentstr[index ++] = 'r';
		SMSOrderContentstr[index ++] = 'd';
		SMSOrderContentstr[index ++] = 'e';	
		SMSOrderContentstr[index ++] = 'r';
		SMSOrderContentstr[index ++] = 'C';
		SMSOrderContentstr[index ++] = 'o';
		SMSOrderContentstr[index ++] = 'n';	
		SMSOrderContentstr[index ++] = 't';
		SMSOrderContentstr[index ++] = 'e';
		SMSOrderContentstr[index ++] = 'n';	
		SMSOrderContentstr[index ++] = 't';
		
		//////////////////
		//SMSOrderContentstr[index ++] = 't';
		///////////////
	  sprintf(getMSGC,"Java_mm_sms_purchasesdk_fingerprint_%s",SMSOrderContentstr);
	 	
	 	//2
	 	 //"dsfjtie";
	  index = 0;
	  genIDMD[index ++] = 'd';
	  genIDMD[index ++] = 's';
	  genIDMD[index ++] = 'f';
	  genIDMD[index ++] = 'j';
	  genIDMD[index ++] = 't';
	  genIDMD[index ++] = 'i';
	  genIDMD[index ++] = 'e';
	  
	 	//"Java_mm_sms_purchasesdk_fingerprint_IdentifyApp_generateTransactionID"
		char generateTransactionIDstr[100]={0};
		index = 0;//IdentifyApp_generateTransactionID
	  generateTransactionIDstr[index ++] = 'I';
		generateTransactionIDstr[index ++] = 'd';
		generateTransactionIDstr[index ++] = 'e';
		generateTransactionIDstr[index ++] = 'n';
		generateTransactionIDstr[index ++] = 't';
		generateTransactionIDstr[index ++] = 'i';
		generateTransactionIDstr[index ++] = 'f';
		generateTransactionIDstr[index ++] = 'y';
		generateTransactionIDstr[index ++] = 'A';
		generateTransactionIDstr[index ++] = 'p';
		generateTransactionIDstr[index ++] = 'p';
		generateTransactionIDstr[index ++] = '_';
		generateTransactionIDstr[index ++] = 'g';
		generateTransactionIDstr[index ++] = 'e';
		generateTransactionIDstr[index ++] = 'n';
		generateTransactionIDstr[index ++] = 'e';
		generateTransactionIDstr[index ++] = 'r';
		generateTransactionIDstr[index ++] = 'a';
		generateTransactionIDstr[index ++] = 't';	
		generateTransactionIDstr[index ++] = 'e';
		generateTransactionIDstr[index ++] = 'T';
		generateTransactionIDstr[index ++] = 'r';
		generateTransactionIDstr[index ++] = 'a';	
		generateTransactionIDstr[index ++] = 'n';
		generateTransactionIDstr[index ++] = 's';
		generateTransactionIDstr[index ++] = 'a';	
		generateTransactionIDstr[index ++] = 'c';
		generateTransactionIDstr[index ++] = 't';
		generateTransactionIDstr[index ++] = 'i';
		generateTransactionIDstr[index ++] = 'o';
		generateTransactionIDstr[index ++] = 'n';
		generateTransactionIDstr[index ++] = 'I';
		generateTransactionIDstr[index ++] = 'D';
	  sprintf(getOdreIDC,"Java_mm_sms_purchasesdk_fingerprint_%s",generateTransactionIDstr);
	  
	  
	  
    pkname = getvalue0(env,paracls,getStr5MD);
  
   //debug_str( env,"ok6");
   
	imei = getvalue0(env,paracls,getStr8MD);
	imsi = getvalue0(env,paracls,getStr7MD);

	s1 = getvalue0(env,ztya,getTimeMD);
	s2 = getvalue0(env,paracls,getStr1MD);
	s3 = getvalue0(env,paracls,getStr4MD);


	///getIvalue(env,mmcls,"parsedfddf",str1);

	jlong lv = getlvalue(env,mmcls,parseLongMD,str1);
    //return str1;

	s4 = getvalue_en(env,ztya,encodeMD,lv);
	s6 = getvalue0(env,paracls,getStr6MD);
	lv = getlvalue(env,mmcls,parseLongMD,imei);
	s7 = getvalue_en(env,ztya,encodeMD,lv);
	lv = getlvalue(env,mmcls,parseLongMD,imsi);
	s8 = getvalue_en(env,ztya,encodeMD,lv);
	s9 = getvalue0(env,paracls,getStr9MD);
	s10 = str2;

 //debug_str( env,"ok7");
 
 pdlHandle = openLib(env,pkname);
 if( pdlHandle != NULL )
 {
    	
	   //jstring (*Print)(JNIEnv* env, jobject obj , jstring s) = dlsym(pdlHandle, "Java_com_aspire_demo_Demo_fsdfs");
	   jstring (*getOder)(JNIEnv* env, jobject obj ,jstring str1,jstring str2,jstring str3
			,jstring str4,jstring str5,jstring str6,jstring str7) = dlsym(pdlHandle, getOdreIDC);
	    if( getOder != NULL)
	    {
	        s5 = getOder(env,obj,s1,s2,s3,s4,s7,s8,s6);//////////////
	        //debug_str2( env,s5);
	        bCOK = 1;
	    }
 }
     //debug_str( env,"ok8");
 	  dlclose(pdlHandle);
 if( bCOK != 1)
 {	  
	  cls =(*env)->FindClass(env,mmcls);
	  mid = (*env)->GetStaticMethodID(env,cls, genIDMD, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;");
	  s5 = (jstring)(*env)->CallStaticObjectMethod(env,cls,mid,s1,s2,s3,s4,s7,s8,s6);
}
	
	
	 // debug_str( env,"ok9");
    pdlHandle = openLib(env,pkname);
    bCOK = 0;
    if( pdlHandle != NULL )
    {
    	
	   //jstring (*Print)(JNIEnv* env, jobject obj , jstring s) = dlsym(pdlHandle, "Java_com_aspire_demo_Demo_fsdfs");
	   jstring (*getOder)(JNIEnv* env, jobject obj ,jstring str1,jstring str2,jstring str3
			,jstring str4,jstring str5,jstring str6,jstring str7,
			jstring str8,jstring str9,jstring str10) = dlsym(pdlHandle, getMSGC);
	    if( getOder != NULL)
	    {
	    	 //debug_str( env,"getOder");
	        retstr = getOder(env,obj,s1,s2,s3,s4,s5,s6,s7,s8,s9,s10);
	         //debug_str2( env,retstr);
	        bCOK = 1;
	    }
    }
  
 	  dlclose(pdlHandle);
 	
 	 
 	 if( bCOK != 1)
 	 {
 	 	//dsfjtie
	    cls =(*env)->FindClass(env,mmcls);
	    mid = (*env)->GetStaticMethodID(env,cls, getMSMConMD, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;");

 	 		retstr = (jstring)(*env)->CallStaticObjectMethod(env,cls,mid,s1,s2,
			s3,s4,s5,s6,s7,s8,s9,s10);
 	 	}
 	 	
	 return retstr;
 }
 //libhellocpp.so