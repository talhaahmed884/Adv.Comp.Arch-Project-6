(RESET)

    @KBD
    D=M

    @BLACK_PAINT
    D;JGT

    @WHITE_PAINT
    D;JEQ

    (BLACK_PAINT)
        @color
        M=-1
        
        @PAINT
        0;JMP

    (WHITE_PAINT)
        @color
        M=0

        @PAINT
        0;JMP

    (PAINT)
        @16384
        D=A
        @screen_start
        M=D

        @24576
        D=A
        @screen_end
        M=D

        (LOOP)

            @screen_end
            D=M

            @screen_start
            D=D-M

            @RESET
            D;JEQ

            @color
            D=M

            @screen_start
            A=M
            M=D

            @screen_start
            M=M+1

            @LOOP
            0;JMP

(END)
    @END
    0;JMP