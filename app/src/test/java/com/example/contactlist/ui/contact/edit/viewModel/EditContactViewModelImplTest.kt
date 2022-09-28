package com.example.contactlist.ui.contact.edit.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.contactlist.MainCoroutineRule
import com.example.contactlist.data.repository.ContactRepository
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock

class EditContactViewModelImplTest {
    private lateinit var viewModel: EditContactViewModelImpl
    private val repo = mock<ContactRepository>()

    @Rule
    @JvmField
    val mainCoroutineRule = MainCoroutineRule()

    @Rule
    @JvmField
    val taskCoroutineRule = InstantTaskExecutorRule()

    @Before
    @OptIn(ExperimentalCoroutinesApi::class)
    fun setUp() {
        mainCoroutineRule.runBlockingTest {
            viewModel = EditContactViewModelImpl(repo)
        }
    }

    @Test
    fun `update should return true when all input fields are filled`() {
        viewModel.name.value = "john doe"
        viewModel.phone.value = "1234567890"
        assertTrue(viewModel.update(0))
    }

}