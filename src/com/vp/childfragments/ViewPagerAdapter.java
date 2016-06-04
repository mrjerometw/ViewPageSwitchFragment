/*
 * Copyright (c) 2014, Rukmal Dias 
All rights reserved. 

Redistribution and use in source and binary forms, with or without 
modification, are permitted provided that the following conditions are met: 

 * Redistributions of source code must retain the above copyright notice, 
   this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright 
   notice, this list of conditions and the following disclaimer in the 
   documentation and/or other materials provided with the distribution. 

THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS'' AND ANY 
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE LIABLE FOR ANY 
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR 
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER 
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT 
LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY 
OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH 
DAMAGE. 
 */
package com.vp.childfragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vp.childfragments.fragments.ChildFragment;
import com.vp.childfragments.fragments.FirstPageFragment;
import com.vp.childfragments.fragments.SecondPageFragment;
import com.vp.childfragments.fragments.ThirdPageFragment;

/**
 * ViewPagerAdapter is an {@link android.support.v4.app.FragmentPagerAdapter FragmentPagerAdapter} 
 * that is used bind some fragments with their child fragments here.
 * @author Rukmal Dias
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
	
    private BaseFragment mSecondFragment; // Fragment at index 1
    private BaseFragment mThreadFragment; // Fragment at index 2
	private final FragmentManager mFragmentManager;
	
	private static final int NUM_OF_ITEMS = 3; // No of ViewPager items
	
	private static final String STR_CHILD_TAG_2 = " -> child fragment of tag 2";
	private static final String STR_CHILD_TAG_3 = " -> child fragment of tag 3";

	public ViewPagerAdapter(FragmentManager fm) {
		super(fm);
		mFragmentManager = fm;
	}

    @Override
    public Fragment getItem(int position) {
    	if(position == 1) {
            if (mSecondFragment == null) {
                mSecondFragment = SecondPageFragment.newInstance(new PageFragmentListener() {
                    public void onSwitchToNextFragment() {
                        mFragmentManager.beginTransaction().remove(mSecondFragment).commit();
                        mSecondFragment = ChildFragment.newInstance(STR_CHILD_TAG_2);
                        mSecondFragment.setShowingChild(true);
                        notifyDataSetChanged();
                    }
                });
            }
            return mSecondFragment;
        }
        else if(position == 2) {
            if (mThreadFragment == null) {
                mThreadFragment = ThirdPageFragment.newInstance(new PageFragmentListener() {
                    public void onSwitchToNextFragment() {
                        mFragmentManager.beginTransaction().remove(mThreadFragment).commit();
                        mThreadFragment = ChildFragment.newInstance(STR_CHILD_TAG_3);
                        mThreadFragment.setShowingChild(true);
                        notifyDataSetChanged();
                    }
                });
            }
            return mThreadFragment;
        }
        
        return FirstPageFragment.newInstance();
    }

	@Override
	public int getCount() {
		return NUM_OF_ITEMS;
	}
	
    @Override
    public int getItemPosition(Object object)
    {
        if (object instanceof SecondPageFragment && mSecondFragment instanceof ChildFragment) {
            return POSITION_NONE;
        }
        else if(object instanceof ThirdPageFragment && mThreadFragment instanceof ChildFragment) {
            return POSITION_NONE;
        }
        else if(object instanceof ChildFragment) {
       	 	return POSITION_NONE;
        }
        return POSITION_UNCHANGED;
    }
	
    public void replaceChildFragment(BaseFragment oldFrg, int position) {
    	switch (position) {
		case 1:
			mFragmentManager.beginTransaction().remove(oldFrg).commit();
	        mSecondFragment = SecondPageFragment.newInstance(new PageFragmentListener() {
	        	public void onSwitchToNextFragment() {
	        		mFragmentManager.beginTransaction().remove(mSecondFragment).commit();
                    mSecondFragment = ChildFragment.newInstance(STR_CHILD_TAG_2);
                    mSecondFragment.setShowingChild(true);
                    notifyDataSetChanged();
	        	}
	        });
	        notifyDataSetChanged();
			break;
				
		case 2:
			mFragmentManager.beginTransaction().remove(oldFrg).commit();
	        mThreadFragment = ThirdPageFragment.newInstance(new PageFragmentListener() {
	        	public void onSwitchToNextFragment() {
	        		mFragmentManager.beginTransaction().remove(mThreadFragment).commit();
	        		mThreadFragment = ChildFragment.newInstance(STR_CHILD_TAG_3);
	        		mThreadFragment.setShowingChild(true);
	        		notifyDataSetChanged();
	        	}
	        });
	        notifyDataSetChanged();
			break;					

		default:
			break;
		}
    }

}
