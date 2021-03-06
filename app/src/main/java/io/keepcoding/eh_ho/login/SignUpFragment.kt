package io.keepcoding.eh_ho.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.common.TextChangedWatcher
import io.keepcoding.eh_ho.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private val vm: LoginViewModel by activityViewModels()
    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false).apply {
            labelSignIn.setOnClickListener {
                vm.moveToSignIn()
            }
            vm.signUpData.observe(viewLifecycleOwner) {
                inputEmail.apply {
                    setText(it.email)
                    setSelection(it.email.length)
                }
                inputUsername.apply {
                    setText(it.userName)
                    setSelection(it.userName.length)
                }
                inputPassword.apply {
                    setText(it.password)
                    setSelection(it.password.length)
                }
                inputConfirmPassword.apply {
                    setText(it.confirmPassword)
                    setSelection(it.confirmPassword.length)
                }
            }
            vm.signUpEnabled.observe(viewLifecycleOwner) {
                buttonSignUp.isEnabled = it
            }
            inputEmail.apply {
                addTextChangedListener(TextChangedWatcher(vm::onNewSignUpEmail))
            }
            inputUsername.apply {
                addTextChangedListener(TextChangedWatcher(vm::onNewSignUpUserName))
            }
            inputPassword.apply {
                addTextChangedListener(TextChangedWatcher(vm::onNewSignUpPassword))
            }
            inputConfirmPassword.apply {
                addTextChangedListener(TextChangedWatcher(vm::onNewSignUpConfirmPassword))
            }
            buttonSignUp.setOnClickListener {
                println("JcLog: clicking signup button")
                vm.signUp()
            }

            vm.action.observe(viewLifecycleOwner) {
                when (it) {
                    is LoginViewModel.Action.ShowInvalidUsername -> {
                        showUsernameError(it.show)
                    }

                    is LoginViewModel.Action.ShowInvalidEmail -> {
                        showEmailError(it.show)
                    }

                    is LoginViewModel.Action.ShowInvalidPassword -> {
                        showPasswordError(it.show)
                    }

                    is LoginViewModel.Action.ShowError -> {
                        Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        return binding.root
    }

    private fun showUsernameError(show: Boolean) {
        binding.layoutUsername.error = if (show) {
            getString(R.string.error_invalid_username)
        } else {
            null
        }
    }

    private fun showEmailError(show: Boolean) {
        binding.layoutEmail.error = if (show) {
            getString(R.string.error_invalid_email)
        } else {
            null
        }
    }

    private fun showPasswordError(show: Boolean) {
        binding.layoutPassword.error = if (show) {
            getString(R.string.error_invalid_password)
        } else {
            null
        }
    }

    companion object {
        fun newInstance(): SignUpFragment = SignUpFragment()
    }
}