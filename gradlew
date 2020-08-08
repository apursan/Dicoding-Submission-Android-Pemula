/*
 * Copyright (C) 2015 The Android Open Source Project
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
 */

package com.google.android.material.navigation;

import com.google.android.material.R;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import androidx.core.content.ContextCompat;
import androidx.customview.view.AbsSavedState;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.widget.TintTypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.internal.NavigationMenuPresenter;
import com.google.android.material.internal.ScrimInsetsFrameLayout;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;

/**
 * Represents a standard navigation menu for application. The menu contents can be populated by a
 * menu resource file.
 *
 * <p>NavigationView is typically placed inside a {@link androidx.drawerlayout.widget.DrawerLayout}.
 *
 * <pre>
 * &lt;androidx.drawerlayout.widget.DrawerLayout
 *     xmlns:android="http://schemas.android.com/apk/res/android"
 *     xmlns:app="http://schemas.android.com/apk/res-auto"
 *     android:id="@+id/drawer_layout"
 *     android:layout_width="match_parent"
 *     android:layout_height="match_parent"
 *     android:fitsSystemWindows="true"&gt;
 *
 *     &lt;!-- Your contents --&gt;
 *
 *     &lt;com.google.android.material.navigation.NavigationView
 *         android:id="@+id/navigation"
 *         android:layout_width="wrap_content"
 *         android:layout_height="match_parent"
 *         android:layout_gravity="start"
 *         app:menu="@menu/my_navigation_items" /&gt;
 * &lt;/androidx.drawerlayout.widget.DrawerLayout&gt;
 * </pre>
 */
public class NavigationView extends ScrimInsetsFrameLayout {

  private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};
  private static final int[] DISABLED_STATE_SET = {-android.R.attr.state_enabled};

  private static final int PRESENTER_NAVIGATION_VIEW_ID = 1;

  @NonNull private final NavigationMenu menu;
  private final NavigationMenuPresenter presenter = new NavigationMenuPresenter();

  OnNavigationItemSelectedListener listener;
  private final int maxWidth;

  private final int[] tmpLocation = new int[2];

  private MenuInflater menuInflater;
  private OnGlobalLayoutListener onGlobalLayoutListener;

  public NavigationView(@NonNull Context context) {
    this(context, null);
  }

  public NavigationView(@NonNull Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, R.attr.navigationViewStyle);
  }

  public NavigationView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    // Create the menu
    this.menu = new NavigationMenu(context);

    // Custom attributes
    TintTypedArray a =
        ThemeEnforcement.obtainTintedStyledAttributes(
            context,
            attrs,
            R.styleable.NavigationView,
            defStyleAttr,
            R.style.Widget_Design_NavigationView);

    if (a.hasValue(R.styleable.NavigationView_android_background)) {
      ViewCompat.setBackground(this, a.getDrawable(R.styleable.NavigationView_android_background));
    }

    // Set the background to a MaterialSha