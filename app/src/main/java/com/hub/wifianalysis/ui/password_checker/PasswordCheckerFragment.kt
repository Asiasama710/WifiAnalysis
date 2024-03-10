package com.hub.wifianalysis.ui.password_checker

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.hub.wifianalysis.R
import com.hub.wifianalysis.databinding.FragmentPasswordCheckerBinding

class PasswordCheckerFragment : Fragment() {

    private lateinit var binding: FragmentPasswordCheckerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPasswordCheckerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.passwordEditText.addTextChangedListener(passwordWatcher)

        binding.checkPasswordButton.setOnClickListener {
            checkPasswordStrength()
        }
    }

    private val passwordWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            binding.passwordStrengthTextView.text = ""
            binding.passwordTextInputLayout.error = null
        }
    }

    private fun checkPasswordStrength() {
        val password = binding.passwordEditText.text.toString()

        val passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"

        if (password.isEmpty()) {
            binding.passwordTextInputLayout.error = "Password cannot be empty"
            return
        }

        if (password.matches(Regex(passwordRegex))) {
            binding.passwordStrengthTextView.text = "Password is strong"
            binding.passwordStrengthTextView.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        } else {
            binding.passwordTextInputLayout.error = "Password must contain at least one digit, one lowercase, one uppercase letter, one special character, and no whitespaces and at least long 8 characters"
            binding.passwordStrengthTextView.text = "Password is weak"
            binding.passwordStrengthTextView.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.md_theme_error
                )
            )
        }
    }
}
