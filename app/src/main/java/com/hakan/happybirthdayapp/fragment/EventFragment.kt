package com.hakan.happybirthdayapp.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hakan.happybirthdayapp.R
import com.hakan.happybirthdayapp.data.User
import com.hakan.happybirthdayapp.data.UserViewModel
import com.hakan.happybirthdayapp.databinding.FragmentEventBinding
import java.util.*


class EventFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    var day = 0
    var month = 0
    var year = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0

    /**
     * [controlFlag] : 0  Ekleme
     * [controlFlag] : 1 Güncelleme
     * */
    var controlFlag = 0
    private var _binding: FragmentEventBinding? = null
    private val binding: FragmentEventBinding get() = _binding!!
    private lateinit var userViewModel: UserViewModel
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handleArguments()


    }

    private fun handleArguments() {
        arguments?.let {
            user = it.getParcelable("user")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setSelectIcon(0)
        handleClickListener()
    }

    private fun handleUpdateProcess(user: User) {
        Log.e(
            EventFragment::class.java.name,
            "onViewCreated: id : ${user.id} name : ${user.name} surname : ${user.surname} " +
                    "depart: ${user.departmentText} date: ${user.date}  not: ${user.descriptor}"
        )

        binding.apply {
            includeUserInfo.apply {
                includeCalender.apply {
                    includeNote.apply {
                        includeSummary.apply {
                            editName.setText(user.name)
                            editSurname.setText(user.surname)
                            spDepart.setSelection(user.departmentPosition)
                            tvtextTime.text = user.date
                            editNote.setText(user.descriptor)
                            saveButton.text = "Güncelle"
                            controlFlag = 1
                        }
                    }
                }
            }
        }
    }

    private fun handleAddProcess() {
        binding.apply {
            includeUserInfo.apply {
                includeCalender.apply {
                    includeNote.apply {
                        includeSummary.apply {
                            saveButton.text = "Kaydet "
                            controlFlag = 0
                        }
                    }
                }
            }
        }
    }

    private fun initialize() {
        userViewModel = UserViewModel(requireContext())
        if (user != null) {
            Log.e(EventFragment::class.java.name, "onViewCreated: guncelleme ")
            handleUpdateProcess(user!!)
        } else {
            Log.e(EventFragment::class.java.name, "onViewCreated: ekleme ")
            handleAddProcess()
        }
    }

    private fun handleClickListener() {
        binding.apply {
            includeUserInfo.apply {
                nextButton.setOnClickListener {
                    handleUserInfoNextButtonClick()
                }
            }
            includeCalender.apply {
                nextButton.setOnClickListener {
                    handleCalenderNextButtonClick()
                }
                backButton.setOnClickListener {
                    handleCalenderBackButtonClick()
                }
                calenderButton.setOnClickListener {
                    pickDateButton()
                }
                materialCardViewCalenderButton.setOnClickListener {
                    pickDateButton()
                }
            }

            includeNote.apply {
                nextButton.setOnClickListener {
                    handleNoteNextButtonClick()
                }
                backButton.setOnClickListener {
                    handleNoteBackButtonClick()
                }
            }

            includeSummary.apply {
                saveButton.setOnClickListener {
                    handleSummarySaveButtonClick()
                }

                backButton.setOnClickListener {
                    handleSummaryBackButtonClick()
                }
            }
        }
    }

    fun setSelectIcon(position: Int) {
        binding.apply {
            userInfoIcon.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    if (position == 0) R.color.apColor else R.color.inactiveIconColor
                ),
                android.graphics.PorterDuff.Mode.MULTIPLY
            )

            calenderIcon.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    if (position == 1) R.color.apColor else R.color.inactiveIconColor
                ),
                android.graphics.PorterDuff.Mode.MULTIPLY
            )

            descriptionIcon.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    if (position == 2) R.color.apColor else R.color.inactiveIconColor
                ),
                android.graphics.PorterDuff.Mode.MULTIPLY
            )

            summaryIcon.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    if (position == 3) R.color.apColor else R.color.inactiveIconColor
                ),
                android.graphics.PorterDuff.Mode.MULTIPLY
            )
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun handleUserInfoNextButtonClick() {
        binding.apply {
            includeUserInfo.apply {
                includeCalender.apply {
                    includeNote.apply {
                        includeSummary.apply {
                            // todo : name, surname, deparment value isnotEmpty
                            user = User(
                                id = 0,
                                name = editName.text.toString(),
                                surname = editSurname.text.toString(),
                                departmentText = spDepart.selectedItem.toString(),
                                departmentPosition = spDepart.selectedItemPosition,
                                date = tvtextTime.text.toString(),
                                descriptor = editNote.text.toString()
                            )
                            includeUserInfoLayout.visibility = View.INVISIBLE
                            includeCalenderLayout.visibility = View.VISIBLE
                            includeUserNoteLayout.visibility = View.INVISIBLE
                            includeUserSummaryLayout.visibility = View.INVISIBLE
                            setSelectBackIcon(3)
                            setSelectIcon(1)
                        }
                    }
                }
            }
        }
    }

    /**
     * Calender Layout visible oldu.
     * @author Hakan Akbudak
     * @since 06.09.2022
     **/
    @SuppressLint("ResourceAsColor")
    private fun handleCalenderNextButtonClick() {
        binding.apply {
            includeCalender.apply {

                user?.date = "$savedDay/$savedMonth/$savedYear"
            }

            includeUserInfoLayout.visibility = View.INVISIBLE
            includeCalenderLayout.visibility = View.INVISIBLE
            includeUserNoteLayout.visibility = View.VISIBLE
            includeUserSummaryLayout.visibility = View.INVISIBLE

            setSelectIcon(2)
        }
    }

    private fun handleNoteNextButtonClick() {
        binding.apply {
           includeUserInfo.apply {
               includeCalender.apply {
                   includeNote.apply {
                       includeSummary.apply {
                           user = User(
                               id = 0,
                               name = editName.text.toString(),
                               surname = editSurname.text.toString(),
                               departmentText = spDepart.selectedItem.toString(),
                               departmentPosition = spDepart.selectedItemPosition,
                               date = tvtextTime.text.toString(),
                               descriptor = editNote.text.toString()
                           )                       }
                   }
               }
           }
            includeUserInfoLayout.visibility = View.INVISIBLE
            includeCalenderLayout.visibility = View.INVISIBLE
            includeUserNoteLayout.visibility = View.INVISIBLE
            includeUserSummaryLayout.visibility = View.VISIBLE

            setSelectIcon(3)
        }
    }

    private fun handleSummarySaveButtonClick() {
        binding.apply {
            includeUserInfo.apply {
                includeCalender.apply {
                    includeNote.apply {
                        includeSummary.apply {
                            if (controlFlag == 0) {
                                handleSaveButtonInsert()
                            } else {
                                handleSaveButtonUpdate()
                            }
                            findNavController().popBackStack()
                        }
                    }
                }
            }
        }
    }

    private fun handleSaveButtonUpdate() {
        user?.let {
            userViewModel.updateUser(it)
        }
    }

    private fun handleSaveButtonInsert() {
        user?.let {
            userViewModel.addUser(it)
        }
    }

    private fun handleCalenderBackButtonClick() {
        binding.apply {

            includeUserInfoLayout.visibility = View.VISIBLE
            includeCalenderLayout.visibility = View.INVISIBLE
            includeUserNoteLayout.visibility = View.INVISIBLE
            includeUserSummaryLayout.visibility = View.INVISIBLE

            setSelectBackIcon(2)
        }
    }

     /**
     * Note Layout geri button
     * @author Hakan Akbudak
     * @since 06.09.2022
     **/
    private fun handleNoteBackButtonClick() {
        binding.apply {
            includeUserInfoLayout.visibility = View.INVISIBLE
            includeCalenderLayout.visibility = View.VISIBLE
            includeUserNoteLayout.visibility = View.INVISIBLE
            includeUserSummaryLayout.visibility = View.INVISIBLE

            setSelectBackIcon(1)
        }
    }

    private fun handleSummaryBackButtonClick() {
        binding.apply {
            includeUserInfoLayout.visibility = View.INVISIBLE
            includeCalenderLayout.visibility = View.INVISIBLE
            includeUserNoteLayout.visibility = View.VISIBLE
            includeUserSummaryLayout.visibility = View.INVISIBLE

            setSelectBackIcon(0)
        }
    }

    //**************************************************************************************************
    private fun getDateTimeCalender() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }

    private fun pickDateButton() {
        getDateTimeCalender()
        DatePickerDialog(requireContext(), this, year, month, day).show()

    }

    /**
     * Tarih kısmında gün ay yıl saat ve dakikayı text içerisine yazdırıyor
     * @author Hakan Akbudak
     * @since 01.09.2022
     **/

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year

        binding.includeCalender.tvtextTime.text =
            "$savedDay/$savedMonth/$savedYear"
    }

//**************************************************************************************************

    private fun handleInsertButtonAdd() {
        binding.apply {
            includeUserInfo.apply {
                includeCalender.apply {
                    includeNote.apply {
                        includeSummary.apply {
                            userViewModel.addUser(
                                User(
                                    id = 0,
                                    name = editName.text.toString(),
                                    surname = editSurname.text.toString(),
                                    departmentText = spDepart.selectedItem.toString(),
                                    departmentPosition = spDepart.selectedItemPosition,
                                    date = tvtextTime.text.toString(),
                                    descriptor = editNote.text.toString()

                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun handleInsertButtonUpdate(user: User) {
        binding.apply {
            includeUserInfo.apply {
                includeCalender.apply {
                    includeNote.apply {
                        includeSummary.apply {
                            userViewModel.updateUser(
                                User(
                                    id = user.id,
                                    name = editName.text.toString(),
                                    surname = editSurname.text.toString(),
                                    departmentText = spDepart.selectedItem.toString(),
                                    departmentPosition = spDepart.selectedItemPosition,
                                    date = tvtextTime.text.toString(),
                                    descriptor = editNote.text.toString()

                                )
                            )
                        }
                    }
                }
            }
        }
    }

/**
 * İleri tuşuna bastığımızda renkleri değişen iconların renklerini
 * back tuşuyla tekrar değiştiriyor.
 * @author Hakan Akbudak
 * @since 08.09.2022
 * */
    fun setSelectBackIcon(position: Int) {
        binding.apply {
            userInfoIcon.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    if (position == 3) R.color.apColor else R.color.inactiveIconColor
                ),
                android.graphics.PorterDuff.Mode.MULTIPLY
            )

            calenderIcon.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    if (position == 2) R.color.apColor else R.color.inactiveIconColor
                ),
                android.graphics.PorterDuff.Mode.MULTIPLY
            )

            descriptionIcon.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    if (position == 1) R.color.apColor else R.color.inactiveIconColor
                ),
                android.graphics.PorterDuff.Mode.MULTIPLY
            )

            summaryIcon.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    if (position == 0) R.color.apColor else R.color.inactiveIconColor
                ),
                android.graphics.PorterDuff.Mode.MULTIPLY
            )
        }
    }

    fun summaryIconRemove(){
        binding.apply {
            includeUserInfo.apply {
                includeCalender.apply {
                    includeNote.apply {
                        includeSummary.apply {



                        }
                    }
                }
            }
        }
    }









}