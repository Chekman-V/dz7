package com.example.dz29d2

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until
import android.content.Intent
import android.content.ComponentName
import androidx.test.uiautomator.Direction

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    //перед запуском тестов надо вручную через adb сделать команду adb push app/src/androidTest/res/raw/base.apk /data/local/tmp/base.apk

    private lateinit var device: UiDevice
    private val packageName = "org.isoron.uhabits"

    @Before
    fun setUp() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        device = UiDevice.getInstance(instrumentation)
        device.pressHome()
        device.executeShellCommand("setprop persist.sys.locale en_US")

        device.executeShellCommand("pm uninstall org.isoron.uhabits")

        val installOutput = device.executeShellCommand(
            "pm install -t -r /data/local/tmp/base.apk"
        )
        println("Вывод установки: $installOutput")

        Thread.sleep(5000)

        val isInstalled = device.executeShellCommand("pm list packages org.isoron.uhabits")
        if (!isInstalled.contains("org.isoron.uhabits")) {
            throw RuntimeException("Установка не удалась: $installOutput")
        }

        var intent = ApplicationProvider.getApplicationContext<Context>()
            .packageManager
            .getLaunchIntentForPackage(packageName)

        if (intent == null) {
            intent = Intent().apply {
                component = ComponentName(packageName, "$packageName.MainActivity")
                action = Intent.ACTION_MAIN
                addCategory(Intent.CATEGORY_LAUNCHER)
            }
        }

        ApplicationProvider.getApplicationContext<Context>().startActivity(intent)

        if (!device.wait(Until.hasObject(By.pkg(packageName).depth(0)), 10000)) {
            throw RuntimeException("Главный экран не загрузился за 10 сек")
        }
    }

    private fun checkElementVisibility(uiObject2: UiObject2?, message: String) {
        if (uiObject2 != null) {
            val bounds = uiObject2.getVisibleBounds()
            assertTrue("$message: элемент не виден пользователю", !bounds.isEmpty)
        } else {
            fail("$message: элемент не найден на экране")
        }
    }

    @Test
    fun test1() {

        val description1: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/description"))
        checkElementVisibility(description1, "Описание на первой странице")

        val title1: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/title"))
        checkElementVisibility(title1, "Заголовок на первой странице")

        val image1: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/image"))
        checkElementVisibility(image1, "Изображение на первой странице")

        val nextButton1: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/next"))
        nextButton1.click()
        Thread.sleep(2000)

        val description2: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/description"))
        checkElementVisibility(description2, "Описание на второй странице")

        val title2: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/title"))
        checkElementVisibility(title2, "Заголовок на второй странице")

        val image2: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/image"))
        checkElementVisibility(image2, "Изображение на второй странице")

        val nextButton2: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/next"))
        nextButton2.click()
        Thread.sleep(2000)

        val description3: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/description"))
        checkElementVisibility(description3, "Описание на третьей странице")

        val title3: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/title"))
        checkElementVisibility(title3, "Заголовок на третьей странице")

        val image3: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/image"))
        checkElementVisibility(image3, "Изображение на третьей странице")

        val doneButton: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/done"))
        doneButton.click()
        Thread.sleep(2000)

        val mainText:UiObject2 = device.findObject(By.text("You have no active habits"))
        checkElementVisibility(mainText,"Текст на главной странице")

        //device.dumpWindowHierarchy(System.out)
    }

    @Test
    fun test2() {

        val textENdescription1: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/description"))
        assertEquals("Loop Habit Tracker helps you create and maintain good habits.",textENdescription1.text)

        val textENtitle1: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/title"))
        assertEquals("Welcome",textENtitle1.text)

        val nextButton1: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/next"))
        nextButton1.click()
        Thread.sleep(2000)

        val textENdescription2: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/description"))
        assertEquals("Every day, after performing your habit, put a checkmark on the app.",textENdescription2.text)

        val textENtitle2: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/title"))
        assertEquals("Create some new habits",textENtitle2.text)

        val nextButton2: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/next"))
        nextButton2.click()
        Thread.sleep(2000)

        val textENdescription3: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/description"))
        assertEquals("Detailed graphs show you how your habits improved over time.",textENdescription3.text)

        val textENtitle3: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/title"))
        assertEquals("Track your progress",textENtitle3.text)

    }

    @Test
    fun test3() {
        val skipButton1: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/skip"))
        skipButton1.click()
        Thread.sleep(2000)

        val mainText:UiObject2 = device.findObject(By.text("You have no active habits"))
        checkElementVisibility(mainText,"Текст на главной странице")
    }

    @Test
    fun test4() {
        val nextButton1: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/next"))
        nextButton1.click()
        Thread.sleep(2000)

        val skipButton2: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/skip"))
        skipButton2.click()
        Thread.sleep(2000)

        val mainText:UiObject2 = device.findObject(By.text("You have no active habits"))
        checkElementVisibility(mainText,"Текст на главной странице")
    }

    @Test
    fun test5() {

        val nextButton1: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/next"))
        nextButton1.click()
        Thread.sleep(2000)

        val nextButton2: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/next"))
        nextButton2.click()
        Thread.sleep(2000)

        val textENtitle3: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/title"))
        assertEquals("Track your progress",textENtitle3.text)

        device.pressBack()
        Thread.sleep(2000)

        val textENtitle2: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/title"))
        assertEquals("Create some new habits",textENtitle2.text)

        device.pressBack()
        Thread.sleep(2000)

        val textENtitle1: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/title"))
        assertEquals("Welcome",textENtitle1.text)

        device.pressBack()
        Thread.sleep(2000)

        val mainText:UiObject2 = device.findObject(By.text("You have no active habits"))
        checkElementVisibility(mainText,"Текст на главной странице")

    }

    @Test
    fun test6() {
        val textENtitle1: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/title"))
        assertEquals("Welcome",textENtitle1.text)

        device.pressRecentApps()
        val appElement: UiObject2 = device.findObject(By.pkg(packageName))
        appElement.swipe(Direction.UP,1.0f)

        Thread.sleep(2000)

        var intent = ApplicationProvider.getApplicationContext<Context>()
            .packageManager
            .getLaunchIntentForPackage(packageName)

        if (intent == null) {
            intent = Intent().apply {
                component = ComponentName(packageName, "$packageName.MainActivity")
                action = Intent.ACTION_MAIN
                addCategory(Intent.CATEGORY_LAUNCHER)
            }
        }

        ApplicationProvider.getApplicationContext<Context>().startActivity(intent)

        Thread.sleep(2000)

        val mainText:UiObject2 = device.findObject(By.text("You have no active habits"))
        checkElementVisibility(mainText,"Текст на главной странице")

        }

    @Test
    fun test7() {
        val nextButton1: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/next"))
        nextButton1.click()
        Thread.sleep(2000)

        val textENtitle2: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/title"))
        assertEquals("Create some new habits",textENtitle2.text)

        device.pressRecentApps()
        val appElement: UiObject2 = device.findObject(By.pkg(packageName))
        appElement.swipe(Direction.UP,1.0f)

        Thread.sleep(2000)

        var intent = ApplicationProvider.getApplicationContext<Context>()
            .packageManager
            .getLaunchIntentForPackage(packageName)

        if (intent == null) {
            intent = Intent().apply {
                component = ComponentName(packageName, "$packageName.MainActivity")
                action = Intent.ACTION_MAIN
                addCategory(Intent.CATEGORY_LAUNCHER)
            }
        }

        ApplicationProvider.getApplicationContext<Context>().startActivity(intent)

        Thread.sleep(2000)

        val mainText:UiObject2 = device.findObject(By.text("You have no active habits"))
        checkElementVisibility(mainText,"Текст на главной странице")
    }

    @Test
    fun test8() {
        val nextButton1: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/next"))
        nextButton1.click()
        Thread.sleep(2000)

        val nextButton2: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/next"))
        nextButton2.click()
        Thread.sleep(2000)

        val textENtitle3: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/title"))
        assertEquals("Track your progress",textENtitle3.text)

        device.pressRecentApps()
        val appElement: UiObject2 = device.findObject(By.pkg(packageName))
        appElement.swipe(Direction.UP,1.0f)
        Thread.sleep(2000)

        var intent = ApplicationProvider.getApplicationContext<Context>()
            .packageManager
            .getLaunchIntentForPackage(packageName)

        if (intent == null) {
            intent = Intent().apply {
                component = ComponentName(packageName, "$packageName.MainActivity")
                action = Intent.ACTION_MAIN
                addCategory(Intent.CATEGORY_LAUNCHER)
            }
        }

        ApplicationProvider.getApplicationContext<Context>().startActivity(intent)

        Thread.sleep(2000)

        val mainText:UiObject2 = device.findObject(By.text("You have no active habits"))
        checkElementVisibility(mainText,"Текст на главной странице")
    }

    @Test
    fun test9() {
        val textENtitle1: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/title"))
        assertEquals("Welcome",textENtitle1.text)

        val appElement1: UiObject2 = device.findObject(By.pkg(packageName))
        appElement1.swipe(Direction.LEFT,1.0f)
        Thread.sleep(2000)

        val textENtitle2: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/title"))
        assertEquals("Create some new habits",textENtitle2.text)

        val appElement2: UiObject2 = device.findObject(By.pkg(packageName))
        appElement2.swipe(Direction.LEFT,1.0f)
        Thread.sleep(2000)

        val textENtitle3: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/title"))
        assertEquals("Track your progress",textENtitle3.text)

        val appElement3: UiObject2 = device.findObject(By.pkg(packageName))
        appElement3.swipe(Direction.RIGHT,1.0f)
        Thread.sleep(2000)

        val textENtitle2Double: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/title"))
        assertEquals("Create some new habits",textENtitle2Double.text)

        val appElement4: UiObject2 = device.findObject(By.pkg(packageName))
        appElement4.swipe(Direction.RIGHT,1.0f)
        Thread.sleep(2000)

        val textENtitle1Double: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/title"))
        assertEquals("Welcome",textENtitle1Double.text)
    }

    @Test
    fun test10() {
        val textENtitle1: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/title"))
        assertEquals("Welcome",textENtitle1.text)

        device.pressHome()
        Thread.sleep(2000)

        var intent = ApplicationProvider.getApplicationContext<Context>()
            .packageManager
            .getLaunchIntentForPackage(packageName)

        if (intent == null) {
            intent = Intent().apply {
                component = ComponentName(packageName, "$packageName.MainActivity")
                action = Intent.ACTION_MAIN
                addCategory(Intent.CATEGORY_LAUNCHER)
            }
        }

        ApplicationProvider.getApplicationContext<Context>().startActivity(intent)
        Thread.sleep(2000)

        checkElementVisibility(textENtitle1, "Заголовок на первой странице")

        val nextButton1: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/next"))
        nextButton1.click()
        Thread.sleep(2000)

        val textENtitle2: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/title"))
        assertEquals("Create some new habits",textENtitle2.text)

        device.pressHome()
        Thread.sleep(1000)

        ApplicationProvider.getApplicationContext<Context>().startActivity(intent)
        Thread.sleep(2000)

        checkElementVisibility(textENtitle2, "Заголовок на второй странице")

        val nextButton2: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/next"))
        nextButton2.click()
        Thread.sleep(2000)

        val textENtitle3: UiObject2 = device.findObject(By.res("org.isoron.uhabits:id/title"))
        assertEquals("Track your progress",textENtitle3.text)

        device.pressHome()
        Thread.sleep(1000)

        ApplicationProvider.getApplicationContext<Context>().startActivity(intent)
        Thread.sleep(2000)

        checkElementVisibility(textENtitle3, "Заголовок на третьей странице")
    }
}
