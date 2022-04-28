package com.ssafy.withssafy.src.main.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.databinding.FragmentHomeBinding
import com.ssafy.withssafy.src.main.MainActivity
import com.ssafy.withssafy.src.viewmodel.HomeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
private const val TAG = "HomeFragment"
// : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home)
class HomeFragment : Fragment(){

    private lateinit var binding : FragmentHomeBinding
    private val homeViewModel: HomeViewModel by activityViewModels()

    private lateinit var mainActivity: MainActivity

    lateinit var favoriteBoardAdapter: FavoriteBoardAdapter
    lateinit var popularPostAdapter: PopularPostAdapter
    lateinit var employInfoAdapter: EmployInfoAdapter

    val studentId = ApplicationClass.sharedPreferencesUtil.getUser().studentId

    // 롤링 배너
    private lateinit var bannerViewPagerAdapter: BannerViewPagerAdapter
    private var isRunning = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bannerList = arrayListOf<Int>(R.drawable.banner1, R.drawable.banner2, R.drawable.banner3)
        homeViewModel.setBannerItems(bannerList)

        setListener()
    }
    private fun setListener(){
        initAdmin()
        initViewPager()
        initButtons()
        subscribeObservers()
        autoScrollViewPage()
        initAdapter()
    }
    private fun initAdmin(){
        Log.d(TAG, "onViewCreated: $studentId")
        if(studentId != null) { // 교육생
            binding.homeLayoutAdminRequestLayout.visibility = View.GONE
        } else { // 관리자
            binding.homeLayoutAdminRequestLayout.visibility = View.VISIBLE
        }
    }
    private fun initButtons(){
        binding.fragmentHomeLinkEduSsafy.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://edu.ssafy.com/comm/login/SecurityLoginForm.do"))
            startActivity(intent)
        }
        binding.fragmentHomeLinkUser.setOnClickListener {
            this@HomeFragment.findNavController().navigate(R.id.userFragment)
        }
        binding.fragmentHomeLinkGitSsafy.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://project.ssafy.com/home"))
            startActivity(intent)
        }
        binding.fragmentHomeLinkJobSsafy.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://job.ssafy.com/job/main/index.do"))
            startActivity(intent)
        }
        binding.fragmentHomeLinkYoutubeSsafy.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UC_XI3ByFO1uZIIH-g-zJZiw"))
            startActivity(intent)
        }
        binding.fragmentHomeMoreJob.setOnClickListener {
            this@HomeFragment.findNavController().navigate(R.id.boardJobFragment)
        }
    }
    private fun initAdapter() {
        favoriteBoardAdapter = FavoriteBoardAdapter()
        binding.homeRvFavoriteBoard.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = favoriteBoardAdapter
        }

        popularPostAdapter = PopularPostAdapter()
        binding.homeRvPopular.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = popularPostAdapter
        }

        employInfoAdapter = EmployInfoAdapter()
        binding.homeRvEmploy.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = employInfoAdapter
        }
    }

    /**
     * home 화면 롤링 배너 Recycler View Adapter
     */
    private fun initViewPager() {
        binding.viewPager.apply {
            bannerViewPagerAdapter = BannerViewPagerAdapter()
            adapter = bannerViewPagerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    isRunning = true
                }
            })
        }
    }

    private fun subscribeObservers() {
        homeViewModel.bannerItemList.observe(mainActivity, Observer{ bannerItemList ->
            bannerViewPagerAdapter.submitList(bannerItemList)

        })
        homeViewModel.currentPosition.observe(mainActivity, Observer { currentPosition ->
            binding.viewPager.currentItem = currentPosition
        })
    }

    private fun autoScrollViewPage() {
        lifecycleScope.launch{
            whenResumed {
                while(isRunning) {
                    delay(3000)
                    homeViewModel.getcurrentPosition()?.let {
                        homeViewModel.setCurrentPosition((it.plus(1)) % 3)
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}