package com.tngtech.jgiven;

import com.tngtech.jgiven.annotation.*;
import com.tngtech.jgiven.format.BooleanFormatter;

public class GivenTestStep extends Stage<GivenTestStep> {

    @ProvidedScenarioState
    int value1;

    @ProvidedScenarioState
    int value2;

    public void some_integer_value( int someIntValue ) {
        this.value1 = someIntValue;
    }

    public void another_integer_value( int anotherValue ) {
        this.value2 = anotherValue;
    }

    public void $d_and_$d( int value1, int value2 ) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public GivenTestStep something() {
        return self();
    }

    public GivenTestStep something_else() {
        return self();
    }

    public GivenTestStep an_array( Object argument ) {
        return self();
    }

    @As( "a step with a (special) description" )
    public GivenTestStep a_step_with_a_description() {
        return self();
    }

    public GivenTestStep a_step_with_a_printf_annotation_$( @Formatf( "%.2f" ) double d ) {
        return self();
    }

    public GivenTestStep a_step_with_a_$_parameter( String param ) {
        return self();
    }

    public GivenTestStep a_step_with_a_boolean_$_parameter( @Format( value = BooleanFormatter.class, args = { "yes", "no" } ) boolean b ) {
        return self();
    }

    @As( "another description" )
    @IntroWord
    public GivenTestStep an_intro_word_with_an_as_annotation() {
        return self();
    }
}
