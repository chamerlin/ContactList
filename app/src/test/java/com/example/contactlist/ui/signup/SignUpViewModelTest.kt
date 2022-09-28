package com.example.contactlist.ui.signup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.contactlist.MainCoroutineRule
import com.example.contactlist.data.model.User
import com.example.contactlist.data.repository.UserRepositoryImpl
import com.jraska.livedata.test
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class SignUpViewModelTest {

    private lateinit var viewModel: SignUpViewModel
    private val repo = UserRepositoryImpl()

    @Rule
    @JvmField
    val mainCoroutineRule = MainCoroutineRule()

    @Rule
    @JvmField
    val taskCoroutineRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        mainCoroutineRule.runBlockingTest {
            viewModel = SignUpViewModel(repo)
        }
    }

    //first name validation
    @Test
    fun `isValidFirstName should return false if firstName contain less than 2 letters`() {
        TestCase.assertEquals(false, viewModel.isValidFirstName("jo"))
    }

    @Test
    fun `isValidFirstName should return false if firstName contain symbols`() {
        TestCase.assertEquals(false, viewModel.isValidFirstName("john#"))
    }

    @Test
    fun `isValidFirstName should return false if firstName contain numbers`() {
        TestCase.assertEquals(false, viewModel.isValidFirstName("john2"))
    }

    //last name validation
    @Test
    fun `isValidLastName should return false if lastName contain less than 2 letters`() {
        TestCase.assertEquals(false, viewModel.isValidLastName("do"))
    }
    @Test
    fun `isValidLastName should return false if lastName contain symbols`() {
        TestCase.assertEquals(false, viewModel.isValidLastName("doe#"))
    }
    @Test
    fun `isValidLastName should return false if lastName contain numbers`() {
        TestCase.assertEquals(false, viewModel.isValidLastName("doe2"))
    }

    //email validation
    @Test
    fun `isValidEmail should return false if email does not have @`() {
        TestCase.assertEquals(false, viewModel.isValidEmail("johndoegmail.com"))
    }
    @Test
    fun `isValidEmail should return false if email's top level domain is less than 2 letters`() {
        TestCase.assertEquals(false, viewModel.isValidEmail("johndoe@gmail.c"))
    }

    //password validation
    @Test
    fun `isValidPassword should return false if password don't contain capital letter`() {
        TestCase.assertEquals(false, viewModel.isValidPassword("#qweqweqwe", "#qweqweqwe"))
    }
    @Test
    fun `isValidPassword should return false if password don't contain at least 8 letters`() {
        TestCase.assertEquals(false, viewModel.isValidPassword("#qweqwe", "#qweqwe"))
    }
    @Test
    fun `isValidPassword should return false if password don't contain special letters`() {
        TestCase.assertEquals(false, viewModel.isValidPassword("Qweqweqwe", "Qweqweqwe"))
    }
    @Test
    fun `isValidPassword should return false if password and confirmPassword is not the same`() {
        TestCase.assertEquals(false, viewModel.isValidPassword("#Qweqweqwe", "#qweqweQWE"))
    }

    //sign up validate input
    @Test
    fun `signUp should return INVALID FIRST NAME if firstName is invalid`() {
        mainCoroutineRule.runBlockingTest {
            TestCase.assertEquals(
                "INVALID FIRST NAME",
                viewModel.signUp(
                    User(
                        firstName = "jo",
                        lastName = "doe",
                        email = "johndoe@gmail.com",
                        password = "#Qweqweqwe",
                        confirmPassword = "#Qweqweqwe"
                    )
                )
            )
        }
    }
    @Test
    fun `signUp should return INVALID LAST NAME if lastName is invalid`() {
        mainCoroutineRule.runBlockingTest {
            TestCase.assertEquals(
                "INVALID LAST NAME",
                viewModel.signUp(
                    User(
                        firstName = "john",
                        lastName = "do",
                        email = "johndoe@gmail.com",
                        password = "#Qweqweqwe",
                        confirmPassword = "#Qweqweqwe"
                    )
                )
            )
        }
    }
    @Test
    fun `signUp should return INVALID EMAIL if email is invalid`() {
        mainCoroutineRule.runBlockingTest {
            TestCase.assertEquals(
                "INVALID EMAIL",
                viewModel.signUp(
                    User(
                        firstName = "john",
                        lastName = "doe",
                        email = "johndoegmail.com",
                        password = "#Qweqweqwe",
                        confirmPassword = "#Qweqweqwe"
                    )
                )
            )
        }
    }
    @Test
    fun `signUp should return INVALID PASSWORD if password is invalid or password and confirmPassword are not the same`() {
        mainCoroutineRule.runBlockingTest {
            TestCase.assertEquals(
                "INVALID PASSWORD",
                viewModel.signUp(
                    User(
                        firstName = "john",
                        lastName = "doe",
                        email = "johndoe@gmail.com",
                        password = "#Qweqweqwe",
                        confirmPassword = "#qweqweqWE"
                    )
                )
            )
        }
    }
    @Test
    fun `signUp should return SUCCESSFUL if all input is valid`() {
        mainCoroutineRule.runBlockingTest {
            TestCase.assertEquals(
                "SUCCESSFUL",
                viewModel.signUp(
                    User(
                        firstName = "john",
                        lastName = "doe",
                        email = "johndoe@gmail.com",
                        password = "#Qweqweqwe",
                        confirmPassword = "#Qweqweqwe"
                    )
                )
            )
        }
    }

    //sign up
    @Test
    fun `signUp should return something if all input is valid`() {
        mainCoroutineRule.runBlockingTest {
            viewModel.signUp(
                User(
                    firstName = "john",
                    lastName = "doe",
                    email = "johndoe@gmail.com",
                    password = "#Qweqweqwe",
                    confirmPassword = "#Qweqweqwe"
                )
            )
            viewModel.onCreateView()
            viewModel.users.test().assertValue { it.isNotEmpty() }
        }
    }

}